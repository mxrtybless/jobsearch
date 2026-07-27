package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.ProfileUpdateDto;
import kg.attractor.jobsearch.dto.UserCreateDto;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("register")
    public ResponseEntity<Void> register(
            @Valid
            @RequestBody
            UserCreateDto userCreateDto
    ) {
        userService.register(
                userCreateDto
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("profile/{id}")
    public ResponseEntity<User> findProfile(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(
                userService.findProfileById(id)
        );
    }

    @PutMapping("edit")
    public ResponseEntity<Void> editProfile(
            @Valid
            @RequestBody
            ProfileUpdateDto profileUpdateDto,
            Authentication authentication
    ) {
        userService.editProfile(
                authentication.getName(),
                profileUpdateDto
        );

        return ResponseEntity
                .ok()
                .build();
    }

    @GetMapping("search/name")
    public ResponseEntity<List<User>>
    findByName(
            @RequestParam String name
    ) {
        return ResponseEntity.ok(
                userService.findByName(name)
        );
    }

    @GetMapping("search/phone")
    public ResponseEntity<List<User>>
    findByPhoneNumber(
            @RequestParam String phoneNumber
    ) {
        return ResponseEntity.ok(
                userService.findByPhoneNumber(
                        phoneNumber
                )
        );
    }

    @GetMapping("search/email")
    public ResponseEntity<User> findByEmail(
            @RequestParam String email
    ) {
        return userService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity
                                .notFound()
                                .build()
                );
    }

    @GetMapping("exists")
    public ResponseEntity<Boolean>
    existsByEmail(
            @RequestParam String email
    ) {
        return ResponseEntity.ok(
                userService.existsByEmail(email)
        );
    }

    @GetMapping("search/applicants")
    public ResponseEntity<List<User>>
    searchApplicants(
            @RequestParam(
                    defaultValue = ""
            )
            String query
    ) {
        return ResponseEntity.ok(
                userService.searchApplicants(
                        query
                )
        );
    }

    @GetMapping("search/employers")
    public ResponseEntity<List<User>>
    searchEmployers(
            @RequestParam(
                    defaultValue = ""
            )
            String query
    ) {
        return ResponseEntity.ok(
                userService.searchEmployers(
                        query
                )
        );
    }

    @PostMapping(
            value = "upload-avatar",
            consumes =
                    MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> uploadAvatar(
            @RequestParam("file")
            MultipartFile file,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                userService.uploadAvatar(
                        authentication.getName(),
                        file
                )
        );
    }
}