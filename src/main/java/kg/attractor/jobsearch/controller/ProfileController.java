package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.ProfileUpdateDto;
import kg.attractor.jobsearch.exception.EmailAlreadyExistsException;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;
    private final ResumeService resumeService;
    private final VacancyService vacancyService;

    @GetMapping
    public String profile(
            Authentication authentication,
            Model model
    ) {
        User user =
                findCurrentUser(
                        authentication
                );

        addUserToModel(
                user,
                model
        );

        if (user.isApplicant()) {
            model.addAttribute(
                    "resumes",
                    resumeService
                            .findByApplicantId(
                                    user.getId()
                            )
            );
        }

        if (user.isEmployer()) {
            model.addAttribute(
                    "vacancies",
                    vacancyService
                            .findByAuthorId(
                                    user.getId()
                            )
            );
        }

        return "profile/profile";
    }

    @GetMapping("edit")
    public String editProfile(
            Authentication authentication,
            Model model
    ) {
        User user =
                findCurrentUser(
                        authentication
                );

        ProfileUpdateDto profileUpdateDto =
                createProfileUpdateDto(
                        user
                );

        model.addAttribute(
                "profileUpdateDto",
                profileUpdateDto
        );

        addUserToModel(
                user,
                model
        );

        return "profile/edit";
    }

    @PostMapping("edit")
    public String editProfile(
            @Valid
            @ModelAttribute("profileUpdateDto")
            ProfileUpdateDto profileUpdateDto,
            BindingResult bindingResult,
            Authentication authentication,
            Model model
    ) {
        User user =
                findCurrentUser(
                        authentication
                );

        if (bindingResult.hasErrors()) {
            addValidationErrors(
                    bindingResult,
                    model
            );

            addUserToModel(
                    user,
                    model
            );

            return "profile/edit";
        }

        String oldEmail =
                user.getEmail();

        try {
            userService.editProfile(
                    oldEmail,
                    profileUpdateDto
            );
        } catch (
                EmailAlreadyExistsException e
        ) {
            Map<String, String> errors =
                    new LinkedHashMap<>();

            errors.put(
                    "email",
                    e.getMessage()
            );

            model.addAttribute(
                    "errors",
                    errors
            );

            addUserToModel(
                    user,
                    model
            );

            return "profile/edit";
        }

        String newEmail =
                profileUpdateDto.getEmail();

        if (newEmail != null
                && !oldEmail.equalsIgnoreCase(
                newEmail
        )) {
            return "redirect:/auth/logout";
        }

        return "redirect:/profile?updated=true";
    }

    @PostMapping("avatar")
    public String uploadAvatar(
            @RequestParam("avatar")
            MultipartFile avatar,
            Authentication authentication
    ) {
        if (avatar == null
                || avatar.isEmpty()) {
            return "redirect:/profile?avatarError=true";
        }

        try {
            userService.uploadAvatar(
                    authentication.getName(),
                    avatar
            );
        } catch (
                IllegalArgumentException e
        ) {
            return "redirect:/profile?avatarError=true";
        }

        return "redirect:/profile?avatarUpdated=true";
    }

    private User findCurrentUser(
            Authentication authentication
    ) {
        return userService
                .findByEmail(
                        authentication.getName()
                )
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Authenticated user not found"
                        )
                );
    }

    private ProfileUpdateDto
    createProfileUpdateDto(
            User user
    ) {
        ProfileUpdateDto dto =
                new ProfileUpdateDto();

        dto.setName(
                user.getName()
        );

        dto.setSurname(
                user.getSurname()
        );

        dto.setAge(
                user.getAge()
        );

        dto.setEmail(
                user.getEmail()
        );

        dto.setPhoneNumber(
                user.getPhoneNumber()
        );

        return dto;
    }

    private void addUserToModel(
            User user,
            Model model
    ) {
        model.addAttribute(
                "user",
                user
        );

        model.addAttribute(
                "isApplicant",
                user.isApplicant()
        );

        model.addAttribute(
                "isEmployer",
                user.isEmployer()
        );
    }

    private void addValidationErrors(
            BindingResult bindingResult,
            Model model
    ) {
        Map<String, String> errors =
                new LinkedHashMap<>();

        bindingResult
                .getFieldErrors()
                .forEach(error ->
                        errors.putIfAbsent(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        model.addAttribute(
                "errors",
                errors
        );
    }
}