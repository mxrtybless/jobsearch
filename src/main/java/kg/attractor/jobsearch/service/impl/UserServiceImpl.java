package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.ProfileDao;
import kg.attractor.jobsearch.dao.UserDao;
import kg.attractor.jobsearch.dto.ProfileUpdateDto;
import kg.attractor.jobsearch.dto.UserCreateDto;
import kg.attractor.jobsearch.exception.EmailAlreadyExistsException;
import kg.attractor.jobsearch.exception.UserNotFoundException;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.service.ImageService;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl
        implements UserService {

    private static final String DEFAULT_AVATAR =
            "default-avatar.png";

    private final UserDao userDao;
    private final ProfileDao profileDao;
    private final ImageService imageService;

    @Override
    public void register(
            UserCreateDto userCreateDto
    ) {
        log.info(
                "Registering user with email: {}",
                userCreateDto.getEmail()
        );

        if (userDao.existsByEmail(
                userCreateDto.getEmail()
        )) {
            log.warn(
                    "Registration failed. Email already exists: {}",
                    userCreateDto.getEmail()
            );

            throw new EmailAlreadyExistsException(
                    userCreateDto.getEmail()
            );
        }

        User user = User.builder()
                .name(userCreateDto.getName())
                .surname(userCreateDto.getSurname())
                .age(userCreateDto.getAge())
                .email(userCreateDto.getEmail())
                .password(userCreateDto.getPassword())
                .phoneNumber(
                        userCreateDto.getPhoneNumber()
                )
                .avatar(DEFAULT_AVATAR)
                .accountType(
                        userCreateDto.getAccountType()
                )
                .build();

        Integer userId = userDao.save(user);

        log.info(
                "User registered successfully with id: {}",
                userId
        );
    }

    @Override
    public User findProfileById(Integer id) {
        log.debug(
                "Searching user profile by id: {}",
                id
        );

        return profileDao.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(id)
                );
    }

    @Override
    public void editProfile(
            Integer id,
            ProfileUpdateDto profileUpdateDto
    ) {
        log.info(
                "Editing user profile with id: {}",
                id
        );

        User user = profileDao.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(id)
                );

        String newEmail =
                profileUpdateDto.getEmail();

        if (newEmail != null
                && !newEmail.equalsIgnoreCase(
                user.getEmail()
        )
                && userDao.existsByEmail(
                newEmail
        )) {

            log.warn(
                    "Profile update failed. Email already exists: {}",
                    newEmail
            );

            throw new EmailAlreadyExistsException(
                    newEmail
            );
        }

        if (profileUpdateDto.getName()
                != null) {
            user.setName(
                    profileUpdateDto.getName()
            );
        }

        if (profileUpdateDto.getSurname()
                != null) {
            user.setSurname(
                    profileUpdateDto.getSurname()
            );
        }

        if (profileUpdateDto.getAge()
                != null) {
            user.setAge(
                    profileUpdateDto.getAge()
            );
        }

        if (profileUpdateDto.getEmail()
                != null) {
            user.setEmail(
                    profileUpdateDto.getEmail()
            );
        }

        if (profileUpdateDto.getPassword()
                != null) {
            user.setPassword(
                    profileUpdateDto.getPassword()
            );
        }

        if (profileUpdateDto.getPhoneNumber()
                != null) {
            user.setPhoneNumber(
                    profileUpdateDto
                            .getPhoneNumber()
            );
        }

        profileDao.update(user);

        log.info(
                "User profile updated successfully. User id: {}",
                id
        );
    }

    @Override
    public List<User> findByName(String name) {
        log.debug(
                "Searching users by name"
        );

        return userDao.findByName(name);
    }

    @Override
    public List<User> findByPhoneNumber(
            String phoneNumber
    ) {
        log.debug(
                "Searching users by phone number"
        );

        return userDao.findByPhoneNumber(
                phoneNumber
        );
    }

    @Override
    public Optional<User> findByEmail(
            String email
    ) {
        log.debug(
                "Searching user by email: {}",
                email
        );

        return userDao.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        log.debug(
                "Checking user existence by email: {}",
                email
        );

        return userDao.existsByEmail(email);
    }

    @Override
    public List<User> searchApplicants(
            String query
    ) {
        log.debug(
                "Searching applicants"
        );

        return userDao.findApplicants(query);
    }

    @Override
    public List<User> searchEmployers(
            String query
    ) {
        log.debug(
                "Searching employers"
        );

        return userDao.findEmployers(query);
    }

    @Override
    public String uploadAvatar(
            Integer userId,
            MultipartFile file
    ) {
        log.info(
                "Uploading avatar for user id: {}",
                userId
        );

        profileDao.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                userId
                        )
                );

        String filename =
                imageService.upload(file);

        userDao.updateAvatar(
                userId,
                filename
        );

        log.info(
                "Avatar uploaded successfully for user id: {}",
                userId
        );

        return filename;
    }
}