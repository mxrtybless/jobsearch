package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.UserCreateDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(
                userService.getAllUsers()
        );
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(
                userService.getUserById(id)
        );
    }

    @GetMapping("/users/search/by-name")
    public ResponseEntity<List<UserDto>> findUsersByName(
            @RequestParam String name
    ) {
        return ResponseEntity.ok(
                userService.findUsersByName(name)
        );
    }

    @GetMapping("/users/search/by-phone")
    public ResponseEntity<List<UserDto>> findUsersByPhoneNumber(
            @RequestParam String phoneNumber
    ) {
        return ResponseEntity.ok(
                userService.findUsersByPhoneNumber(phoneNumber)
        );
    }

    @GetMapping("/users/search/by-email")
    public ResponseEntity<UserDto> findUserByEmail(
            @RequestParam String email
    ) {
        return ResponseEntity.ok(
                userService.findUserByEmail(email)
        );
    }

    @GetMapping("/users/exists")
    public ResponseEntity<Boolean> existsByEmail(
            @RequestParam String email
    ) {
        return ResponseEntity.ok(
                userService.existsByEmail(email)
        );
    }

    @GetMapping("/applicants/search")
    public ResponseEntity<List<UserDto>> searchApplicants(
            @RequestParam(defaultValue = "") String query
    ) {
        return ResponseEntity.ok(
                userService.searchApplicants(query)
        );
    }

    @GetMapping("/employers/search")
    public ResponseEntity<List<UserDto>> searchEmployers(
            @RequestParam(defaultValue = "") String query
    ) {
        return ResponseEntity.ok(
                userService.searchEmployers(query)
        );
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(
            @RequestBody UserCreateDto dto
    ) {
        UserDto user = userService.createUser(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(user);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(
            IllegalArgumentException exception
    ) {
        return ResponseEntity.badRequest()
                .body(Map.of(
                        "error",
                        exception.getMessage()
                ));
    }
}