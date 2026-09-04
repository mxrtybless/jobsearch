package kg.attractor.jobsearch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public DaoAuthenticationProvider
    authenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(
                        userDetailsService
                );

        provider.setPasswordEncoder(
                passwordEncoder
        );

        return provider;
    }

    @Bean
    public AuthenticationManager
    authenticationManager(
            DaoAuthenticationProvider
                    authenticationProvider
    ) {
        return new ProviderManager(
                List.of(authenticationProvider)
        );
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            DaoAuthenticationProvider
                    authenticationProvider
    ) throws Exception {

        http
                .authenticationProvider(
                        authenticationProvider
                )

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.IF_REQUIRED
                        )
                )

                .httpBasic(
                        Customizer.withDefaults()
                )

                .formLogin(login ->
                        login
                                .loginPage(
                                        "/auth/login"
                                )
                                .loginProcessingUrl(
                                        "/auth/login"
                                )
                                .successHandler(
                                        (
                                                request,
                                                response,
                                                authentication
                                        ) ->
                                                response.sendRedirect(
                                                        successUrl(
                                                                authentication
                                                        )
                                                )
                                )
                                .failureUrl(
                                        "/auth/login?error=true"
                                )
                                .permitAll()
                )

                .logout(logout ->
                        logout
                                .logoutUrl(
                                        "/auth/logout"
                                )
                                .logoutSuccessUrl(
                                        "/auth/login?logout=true"
                                )
                                .clearAuthentication(true)
                                .invalidateHttpSession(true)
                                .permitAll()
                )

                .csrf(
                        Customizer.withDefaults()
                )

                .authorizeHttpRequests(authorize ->
                        authorize

                                .requestMatchers(
                                        "/auth/login",
                                        "/auth/register",
                                        "/auth/forgot_password",
                                        "/auth/reset_password",
                                        "/",
                                        "/css/**",
                                        "/images/**",
                                        "/error"
                                )
                                .permitAll()

                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/users/register"
                                )
                                .permitAll()

                                .requestMatchers(
                                        "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/v3/api-docs/**"
                                )
                                .permitAll()

                                .requestMatchers(
                                        "/resumes/form/**"
                                )
                                .hasAnyAuthority(
                                        "APPLICANT",
                                        "ROLE_APPLICANT"
                                )

                                .requestMatchers(
                                        "/vacancies/form/**"
                                )
                                .hasAnyAuthority(
                                        "EMPLOYER",
                                        "ROLE_EMPLOYER"
                                )

                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/companies/**"
                                )
                                .hasAnyAuthority(
                                        "APPLICANT",
                                        "ROLE_APPLICANT"
                                )

                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/vacancies/**"
                                )
                                .permitAll()

                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/resumes"
                                )
                                .hasAnyAuthority(
                                        "EMPLOYER",
                                        "ROLE_EMPLOYER"
                                )

                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/resumes/create"
                                )
                                .hasAnyAuthority(
                                        "APPLICANT",
                                        "ROLE_APPLICANT"
                                )

                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/resumes/edit/**"
                                )
                                .hasAnyAuthority(
                                        "APPLICANT",
                                        "ROLE_APPLICANT"
                                )

                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/resumes/delete/**"
                                )
                                .hasAnyAuthority(
                                        "APPLICANT",
                                        "ROLE_APPLICANT"
                                )

                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/vacancies/create"
                                )
                                .hasAnyAuthority(
                                        "EMPLOYER",
                                        "ROLE_EMPLOYER"
                                )

                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/vacancies/edit/**"
                                )
                                .hasAnyAuthority(
                                        "EMPLOYER",
                                        "ROLE_EMPLOYER"
                                )

                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/vacancies/delete/**"
                                )
                                .hasAnyAuthority(
                                        "EMPLOYER",
                                        "ROLE_EMPLOYER"
                                )

                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/responses/create"
                                )
                                .hasAnyAuthority(
                                        "APPLICANT",
                                        "ROLE_APPLICANT"
                                )

                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/responses/search/vacancy/**"
                                )
                                .hasAnyAuthority(
                                        "EMPLOYER",
                                        "ROLE_EMPLOYER"
                                )

                                .anyRequest()
                                .authenticated()
                );

        return http.build();
    }

    private String successUrl(
            Authentication authentication
    ) {
        boolean employer =
                authentication
                        .getAuthorities()
                        .stream()
                        .anyMatch(authority ->
                                authority.getAuthority()
                                        .equals("EMPLOYER")
                                        || authority.getAuthority()
                                        .equals("ROLE_EMPLOYER")
                        );

        if (employer) {
            return "/resumes";
        }

        return "/vacancies";
    }
}