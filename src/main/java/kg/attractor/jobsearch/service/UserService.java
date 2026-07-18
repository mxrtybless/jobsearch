package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.UserCreateDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.model.AccountType;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final String DEFAULT_AVATAR = "default-avatar.png";

    private final UserRepository userRepository;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public UserDto getUserById(Integer id) {
        User user = getUserModelById(id);
        return toDto(user);
    }

    public List<UserDto> findUsersByName(String name) {
        if (isBlank(name)) {
            throw new IllegalArgumentException("Name is required.");
        }

        return userRepository.findByName(name.trim())
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<UserDto> findUsersByPhoneNumber(String phoneNumber) {
        if (isBlank(phoneNumber)) {
            throw new IllegalArgumentException("Phone number is required.");
        }

        return userRepository.findByPhoneNumber(phoneNumber.trim())
                .stream()
                .map(this::toDto)
                .toList();
    }

    public UserDto findUserByEmail(String email) {
        if (isBlank(email)) {
            throw new IllegalArgumentException("Email is required.");
        }

        User user = userRepository.findByEmail(email.trim())
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found.")
                );

        return toDto(user);
    }

    public boolean existsByEmail(String email) {
        if (isBlank(email)) {
            throw new IllegalArgumentException("Email is required.");
        }

        return userRepository.existsByEmail(email.trim());
    }

    public List<UserDto> searchApplicants(String query) {
        return searchUsersByAccountType(query, AccountType.APPLICANT);
    }

    public List<UserDto> searchEmployers(String query) {
        return searchUsersByAccountType(query, AccountType.EMPLOYER);
    }

    public UserDto createUser(UserCreateDto dto) {
        validateUserCreateDto(dto);

        String email = dto.getEmail().trim();

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException(
                    "User with this email already exists."
            );
        }

        User user = User.builder()
                .name(dto.getName().trim())
                .surname(dto.getSurname().trim())
                .age(dto.getAge())
                .email(email)
                .password(dto.getPassword())
                .phoneNumber(dto.getPhoneNumber().trim())
                .avatar(DEFAULT_AVATAR)
                .accountType(dto.getAccountType())
                .build();

        User savedUser = userRepository.save(user);

        return toDto(savedUser);
    }

    public User getUserModelById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("User id is required.");
        }

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found.")
                );
    }

    private List<UserDto> searchUsersByAccountType(
            String query,
            AccountType accountType
    ) {
        String normalizedQuery = query == null
                ? ""
                : query.trim().toLowerCase();

        return userRepository.findAll()
                .stream()
                .filter(user -> user.getAccountType() == accountType)
                .filter(user ->
                        containsIgnoreCase(
                                user.getName(),
                                normalizedQuery
                        )
                                || containsIgnoreCase(
                                user.getSurname(),
                                normalizedQuery
                        )
                                || containsIgnoreCase(
                                user.getEmail(),
                                normalizedQuery
                        )
                                || containsIgnoreCase(
                                user.getPhoneNumber(),
                                normalizedQuery
                        )
                )
                .map(this::toDto)
                .toList();
    }

    private boolean containsIgnoreCase(String value, String query) {
        if (value == null) {
            return false;
        }

        return value.toLowerCase().contains(query);
    }

    private void validateUserCreateDto(UserCreateDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException(
                    "Request body is required."
            );
        }

        if (isBlank(dto.getName())) {
            throw new IllegalArgumentException("Name is required.");
        }

        if (isBlank(dto.getSurname())) {
            throw new IllegalArgumentException("Surname is required.");
        }

        if (dto.getAge() == null || dto.getAge() < 14) {
            throw new IllegalArgumentException(
                    "Age must be at least 14."
            );
        }

        if (isBlank(dto.getEmail())) {
            throw new IllegalArgumentException("Email is required.");
        }

        if (isBlank(dto.getPassword())) {
            throw new IllegalArgumentException(
                    "Password is required."
            );
        }

        if (isBlank(dto.getPhoneNumber())) {
            throw new IllegalArgumentException(
                    "Phone number is required."
            );
        }

        if (dto.getAccountType() == null) {
            throw new IllegalArgumentException(
                    "Account type is required."
            );
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .age(user.getAge())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .avatar(user.getAvatar())
                .accountType(user.getAccountType())
                .build();
    }
}