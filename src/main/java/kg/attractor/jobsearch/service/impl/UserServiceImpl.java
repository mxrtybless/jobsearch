package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.ProfileDao;
import kg.attractor.jobsearch.dao.UserDao;
import kg.attractor.jobsearch.dto.ProfileUpdateDto;
import kg.attractor.jobsearch.dto.UserCreateDto;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.service.ImageService;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

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
        if (userDao.existsByEmail(
                userCreateDto.getEmail()
        )) {
            throw new IllegalArgumentException(
                    "User with this email already exists"
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

        userDao.save(user);
    }

    @Override
    public User findProfileById(Integer id) {
        return profileDao.findById(id)
                .orElseThrow();
    }

    @Override
    public void editProfile(
            Integer id,
            ProfileUpdateDto profileUpdateDto
    ) {
        User user = profileDao.findById(id)
                .orElseThrow();

        String newEmail =
                profileUpdateDto.getEmail();

        if (newEmail != null
                && !newEmail.equalsIgnoreCase(
                user.getEmail()
        )
                && userDao.existsByEmail(
                newEmail
        )) {

            throw new IllegalArgumentException(
                    "User with this email already exists"
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
    }

    @Override
    public List<User> findByName(String name) {
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
    public boolean existsByEmail(String email) {
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
            Integer userId,
            MultipartFile file
    ) {
        profileDao.findById(userId)
                .orElseThrow();

        String filename =
                imageService.upload(file);

        userDao.updateAvatar(
                userId,
                filename
        );

        return filename;
    }
}