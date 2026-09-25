package kg.attractor.jobsearch.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.UserCreateDto;
import kg.attractor.jobsearch.exception.EmailAlreadyExistsException;
import kg.attractor.jobsearch.model.AccountType;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

import java.io.UnsupportedEncodingException;

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
                    "auth.register.validation.surname.required"
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
                    "auth.register.validation.email.exists"
            );

            return "auth/register";
        } catch (
                IllegalArgumentException e
        ) {
            model.addAttribute(
                    "avatarErrorKey",
                    "auth.register.avatar.error"
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

    private final kg.attractor.jobsearch.service.PasswordRecoveryService recoveryService;

    @org.springframework.beans.factory.annotation.Value("${jobsearch.recovery.demo:false}")
    private boolean recoveryDemo;

    @GetMapping("forgot_password")
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("recovery", new kg.attractor.jobsearch.dto.PasswordRecoveryDto());
        model.addAttribute("demo", recoveryDemo);
        return "auth/forgot_password_form";
    }

    @PostMapping("forgot_password")
    public String processForgotPassword(
            @Valid @ModelAttribute("recovery") kg.attractor.jobsearch.dto.PasswordRecoveryDto form,
            BindingResult errors, HttpServletRequest request, Model model) {
        model.addAttribute("demo", recoveryDemo);
        if (errors.hasErrors()) {
            model.addAttribute("invalidEmail", true);
            return "auth/forgot_password_form";
        }
        if (recoveryDemo) {
            String token = recoveryService.issue(form.getEmail());
            if (token != null) model.addAttribute("demoToken", token);
            else model.addAttribute("errorKey", "auth.forgot.error.userNotFound");
            return "auth/forgot_password_form";
        }
        try {
            userService.makeResetPasswdLink(request);
            model.addAttribute("messageKey", "auth.forgot.success");
        } catch (UsernameNotFoundException e) {
            model.addAttribute("messageKey", "auth.forgot.success");
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("errorKey", "auth.forgot.error.email");
        }
        return "auth/forgot_password_form";
    }

    @GetMapping("reset_password")
    public String showResetPasswordForm(@RequestParam(required = false) String token, Model model) {
        var form = new kg.attractor.jobsearch.dto.PasswordResetDto();
        form.setToken(token);
        model.addAttribute("passwordResetDto", form);
        if (!recoveryService.valid(token)) model.addAttribute("invalidToken", true);
        return "auth/reset_password_form";
    }

    @PostMapping("reset_password")
    public String processResetPassword(
            @Valid @ModelAttribute("passwordResetDto") kg.attractor.jobsearch.dto.PasswordResetDto form,
            BindingResult errors, Model model) {
        if (!recoveryService.valid(form.getToken())) {
            model.addAttribute("invalidToken", true);
            return "auth/reset_password_form";
        }
        if (errors.hasErrors()) return "auth/reset_password_form";
        if (!recoveryService.reset(form.getToken(), form.getPassword())) {
            model.addAttribute("invalidToken", true);
            return "auth/reset_password_form";
        }
        model.addAttribute("messageKey", "auth.reset.success");
        return "message";
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
                authenticationManager
                        .authenticate(
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
