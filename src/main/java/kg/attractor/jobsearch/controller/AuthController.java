package kg.attractor.jobsearch.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.UserCreateDto;
import kg.attractor.jobsearch.exception.EmailAlreadyExistsException;
import kg.attractor.jobsearch.model.AccountType;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager
            authenticationManager;

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
            Model model,
            HttpServletRequest request
    ) {
        if (userCreateDto.getAccountType()
                == AccountType.APPLICANT
                && (userCreateDto.getSurname() == null
                || userCreateDto.getSurname().isBlank())) {
            bindingResult.rejectValue(
                    "surname",
                    "surname.required",
                    "Surname must not be blank"
            );
        }

        if (bindingResult.hasErrors()) {
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
            bindingResult.rejectValue(
                    "email",
                    "email.exists",
                    e.getMessage()
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

        authenticateRegisteredUser(
                userCreateDto,
                request
        );

        if (userCreateDto.getAccountType()
                == AccountType.EMPLOYER) {
            return "redirect:/resumes";
        }

        return "redirect:/vacancies";
    }

    private void authenticateRegisteredUser(
            UserCreateDto userCreateDto,
            HttpServletRequest request
    ) {
        UsernamePasswordAuthenticationToken
                authenticationRequest =
                new UsernamePasswordAuthenticationToken(
                        userCreateDto.getEmail(),
                        userCreateDto.getPassword()
                );

        Authentication authentication =
                authenticationManager.authenticate(
                        authenticationRequest
                );

        SecurityContext securityContext =
                SecurityContextHolder
                        .createEmptyContext();

        securityContext.setAuthentication(
                authentication
        );

        SecurityContextHolder.setContext(
                securityContext
        );

        request.getSession(true)
                .setAttribute(
                        HttpSessionSecurityContextRepository
                                .SPRING_SECURITY_CONTEXT_KEY,
                        securityContext
                );
    }
}
