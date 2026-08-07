package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.UserCreateDto;
import kg.attractor.jobsearch.exception.EmailAlreadyExistsException;
import kg.attractor.jobsearch.model.AccountType;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
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

@Controller
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("register")
    public String register(
            Model model
    ) {
        UserCreateDto userCreateDto =
                new UserCreateDto();

        userCreateDto.setAccountType(
                AccountType.APPLICANT
        );

        model.addAttribute(
                "userCreateDto",
                userCreateDto
        );

        return "auth/register";
    }

    @PostMapping("register")
    public String register(
            @Valid
            @ModelAttribute("userCreateDto")
            UserCreateDto userCreateDto,
            BindingResult bindingResult,
            @RequestParam(
                    name = "avatar",
                    required = false
            )
            MultipartFile avatar,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            addValidationErrors(
                    bindingResult,
                    model
            );

            return "auth/register";
        }

        try {
            userService.register(
                    userCreateDto,
                    avatar
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

            return "auth/register";
        } catch (
                IllegalArgumentException e
        ) {
            model.addAttribute(
                    "avatarError",
                    e.getMessage()
            );

            return "auth/register";
        }

        return "redirect:/auth/login?registered=true";
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