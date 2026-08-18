package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository
        extends JpaRepository<Role, Integer> {

    Optional<Role> findByRole(
            String role
    );
}