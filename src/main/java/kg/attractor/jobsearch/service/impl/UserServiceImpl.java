package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.UserDao;
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
        String filename =
                imageService.upload(file);

        userDao.updateAvatar(
                userId,
                filename
        );

        return filename;
    }
}