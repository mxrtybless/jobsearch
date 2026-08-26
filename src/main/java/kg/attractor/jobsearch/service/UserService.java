package kg.attractor.jobsearch.service;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import kg.attractor.jobsearch.dto.ProfileUpdateDto;
import kg.attractor.jobsearch.dto.UserCreateDto;
import kg.attractor.jobsearch.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    void register(
            UserCreateDto userCreateDto
    );

    void register(
            UserCreateDto userCreateDto,
            MultipartFile avatar
    );

    User findProfileById(Integer id);

    void editProfile(
            String userEmail,
            ProfileUpdateDto profileUpdateDto
    );

    List<User> findByName(String name);

    List<User> findByPhoneNumber(
            String phoneNumber
    );

    Optional<User> findByEmail(
            String email
    );

    boolean existsByEmail(String email);

    List<User> searchApplicants(
            String query
    );

    List<User> searchEmployers(
            String query
    );

    Page<User> findEmployers(Pageable pageable);

    String uploadAvatar(
            String userEmail,
            MultipartFile file
    );

    User getByResetPasswordToken(String token);

    void updatePassword(
            User user,
            String newPassword
    );

    void makeResetPasswdLink(
            HttpServletRequest request
    ) throws UsernameNotFoundException,
            MessagingException,
            UnsupportedEncodingException;
}
