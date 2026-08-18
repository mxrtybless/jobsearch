package kg.attractor.jobsearch.config;

import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(
            UserRepository userRepository
    ) {
        return email -> {
            User user = userRepository
                    .findByEmailIgnoreCase(email)
                    .orElseThrow(() ->
                            new UsernameNotFoundException(
                                    "User with email "
                                            + email
                                            + " not found"
                            )
                    );

            String authority = user
                    .getRole()
                    .getAuthority()
                    .getAuthority();

            return org.springframework.security
                    .core.userdetails.User
                    .withUsername(user.getEmail())
                    .password(user.getPassword())
                    .authorities(authority)
                    .disabled(
                            !Boolean.TRUE.equals(
                                    user.getEnabled()
                            )
                    )
                    .build();
        };
    }

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
                                .defaultSuccessUrl(
                                        "/vacancies",
                                        true
                                )
                                .failureUrl(
                                        "/auth/login?error=true"
                                )
                                .permitAll()
                )

                .logout(logout ->
                        logout
                                .logoutRequestMatcher(
                                        PathPatternRequestMatcher
                                                .withDefaults()
                                                .matcher(
                                                        "/auth/logout"
                                                )
                                )
                                .logoutSuccessUrl(
                                        "/auth/login?logout=true"
                                )
                                .permitAll()
                )

                .csrf(
                        AbstractHttpConfigurer::disable
                )

                .authorizeHttpRequests(authorize ->
                        authorize

                                .requestMatchers(
                                        "/auth/login",
                                        "/auth/register",
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
}
