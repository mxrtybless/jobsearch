package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.ProfileUpdateDto;
import kg.attractor.jobsearch.dto.UserCreateDto;
import kg.attractor.jobsearch.exception.EmailAlreadyExistsException;
import kg.attractor.jobsearch.exception.UserNotFoundException;
import kg.attractor.jobsearch.model.AccountType;
import kg.attractor.jobsearch.model.Role;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.repository.RoleRepository;
import kg.attractor.jobsearch.repository.UserRepository;
import kg.attractor.jobsearch.service.ImageService;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
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
        if (userRepository.existsByEmailIgnoreCase(
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

        AccountType accountType =
                userCreateDto.getAccountType();

        Role role = roleRepository
                .findByRole(
                        accountType.name()
                )
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Role "
                                        + accountType.name()
                                        + " not found"
                        )
                );

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
                        accountType
                )
                .enabled(true)
                .role(role)
                .build();

        User savedUser =
                userRepository.save(user);

        log.info(
                "User registered successfully with id: {}",
                savedUser.getId()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public User findProfileById(
            Integer id
    ) {
        return userRepository
                .findById(id)
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

        userRepository.save(user);

        log.info(
                "User profile updated successfully. User id: {}",
                user.getId()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByName(
            String name
    ) {
        return userRepository
                .findByNameContainingIgnoreCase(
                        name
                );
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByPhoneNumber(
            String phoneNumber
    ) {
        return userRepository
                .findByPhoneNumberContaining(
                        phoneNumber
                );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(
            String email
    ) {
        return userRepository
                .findByEmailIgnoreCase(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(
            String email
    ) {
        return userRepository
                .existsByEmailIgnoreCase(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> searchApplicants(
            String query
    ) {
        return userRepository
                .searchByAccountType(
                        AccountType.APPLICANT,
                        query
                );
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> searchEmployers(
            String query
    ) {
        return userRepository
                .searchByAccountType(
                        AccountType.EMPLOYER,
                        query
                );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findEmployers(
            Pageable pageable
    ) {
        return userRepository.findByAccountType(
                AccountType.EMPLOYER,
                pageable
        );
    }

    @Override
    @Transactional
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

        user.setAvatar(filename);
        userRepository.save(user);

        return filename;
    }

    private User findAuthenticatedUser(
            String userEmail
    ) {
        return userRepository
                .findByEmailIgnoreCase(
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
