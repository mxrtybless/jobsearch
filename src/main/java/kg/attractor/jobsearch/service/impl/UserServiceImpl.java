package kg.attractor.jobsearch.service.impl;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl
        implements UserService {

    private static final String DEFAULT_AVATAR =
            "default-avatar.png";

    private final UserDao userDao;
    private final ImageService imageService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void register(
            UserCreateDto userCreateDto
    ) {
        register(
                userCreateDto,
                null
        );
    }

    @Override
    @Transactional
    public void register(
            UserCreateDto userCreateDto,
            MultipartFile avatar
    ) {
        if (userDao.existsByEmail(
                userCreateDto.getEmail()
        )) {
            throw new EmailAlreadyExistsException(
                    userCreateDto.getEmail()
            );
        }

        String avatarFilename =
                DEFAULT_AVATAR;

        if (avatar != null
                && !avatar.isEmpty()) {

            avatarFilename =
                    imageService.upload(
                            avatar
                    );
        }

        User user = User.builder()
                .name(
                        userCreateDto.getName()
                )
                .surname(
                        userCreateDto.getSurname()
                )
                .age(
                        userCreateDto.getAge()
                )
                .email(
                        userCreateDto.getEmail()
                )
                .password(
                        passwordEncoder.encode(
                                userCreateDto
                                        .getPassword()
                        )
                )
                .phoneNumber(
                        userCreateDto
                                .getPhoneNumber()
                )
                .avatar(
                        avatarFilename
                )
                .accountType(
                        userCreateDto
                                .getAccountType()
                )
                .build();

        Integer userId =
                userDao.save(user);

        log.info(
                "User registered successfully with id: {}",
                userId
        );
    }

    @Override
    public User findProfileById(
            Integer id
    ) {
        return userDao.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(id)
                );
    }

    @Override
    @Transactional
    public void editProfile(
            String userEmail,
            ProfileUpdateDto profileUpdateDto
    ) {
        User user =
                findAuthenticatedUser(
                        userEmail
                );

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

        userDao.update(user);

        log.info(
                "User profile updated successfully. User id: {}",
                user.getId()
        );
    }

    @Override
    public List<User> findByName(
            String name
    ) {
        return userDao.findByName(name);
    }

    @Override
    public List<User> findByPhoneNumber(
            String phoneNumber
    ) {
        return userDao.findByPhoneNumber(
                phoneNumber
        );
    }

    @Override
    public Optional<User> findByEmail(
            String email
    ) {
        return userDao.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(
            String email
    ) {
        return userDao.existsByEmail(email);
    }

    @Override
    public List<User> searchApplicants(
            String query
    ) {
        return userDao.findApplicants(query);
    }

    @Override
    public List<User> searchEmployers(
            String query
    ) {
        return userDao.findEmployers(query);
    }

    @Override
    public String uploadAvatar(
            String userEmail,
            MultipartFile file
    ) {
        User user =
                findAuthenticatedUser(
                        userEmail
                );

        String filename =
                imageService.upload(file);

        userDao.updateAvatar(
                user.getId(),
                filename
        );

        return filename;
    }

    private User findAuthenticatedUser(
            String userEmail
    ) {
        return userDao.findByEmail(
                        userEmail
                )
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "User with email "
                                        + userEmail
                                        + " not found"
                        )
                );
    }
}