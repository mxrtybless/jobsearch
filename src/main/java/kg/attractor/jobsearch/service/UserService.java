package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ProfileUpdateDto;
import kg.attractor.jobsearch.dto.UserCreateDto;
import kg.attractor.jobsearch.model.User;
import org.springframework.web.multipart.MultipartFile;

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

    String uploadAvatar(
            String userEmail,
            MultipartFile file
    );
}