package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.AccountType;
import kg.attractor.jobsearch.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository
        extends JpaRepository<User, Integer> {

    @EntityGraph(
            attributePaths = {
                    "role",
                    "role.authority"
            }
    )
    Optional<User> findByEmailIgnoreCase(
            String email
    );

    boolean existsByEmailIgnoreCase(
            String email
    );

    Optional<User> findByResetPasswordToken(
            String token
    );

    List<User> findByNameContainingIgnoreCase(
            String name
    );

    List<User> findByPhoneNumberContaining(
            String phoneNumber
    );

    Page<User> findByAccountType(
            AccountType accountType,
            Pageable pageable
    );

    @Query("""
            select u
            from User u
            where u.accountType = :accountType
              and (
                    lower(u.name) like lower(concat('%', :query, '%'))
                    or lower(coalesce(u.surname, '')) like lower(concat('%', :query, '%'))
                    or lower(u.email) like lower(concat('%', :query, '%'))
                    or u.phoneNumber like concat('%', :query, '%')
              )
            """)
    List<User> searchByAccountType(
            @Param("accountType")
            AccountType accountType,
            @Param("query")
            String query
    );

    @org.springframework.data.jpa.repository.Modifying(clearAutomatically = true)
    @org.springframework.data.jpa.repository.Query("""
            update User u set u.password = :password,
                u.resetPasswordToken = null, u.resetPasswordExpiresAt = null
            where u.resetPasswordToken = :token and u.resetPasswordExpiresAt > :now
            """)
    int consumeResetToken(@org.springframework.data.repository.query.Param("token") String token,
                          @org.springframework.data.repository.query.Param("password") String password,
                          @org.springframework.data.repository.query.Param("now") java.time.LocalDateTime now);

}
