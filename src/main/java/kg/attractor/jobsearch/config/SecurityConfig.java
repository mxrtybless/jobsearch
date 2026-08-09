package kg.attractor.jobsearch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(
            DataSource dataSource
    ) {
        String usersByEmailQuery = """
                SELECT
                    email AS username,
                    password,
                    enabled
                FROM users
                WHERE LOWER(email) = LOWER(?)
                """;

        String authoritiesByEmailQuery = """
                SELECT
                    u.email AS username,
                    a.authority
                FROM users u
                JOIN roles r
                    ON r.id = u.role_id
                JOIN authorities a
                    ON a.id = r.authority_id
                WHERE LOWER(u.email) = LOWER(?)
                """;

        JdbcUserDetailsManager userDetailsManager =
                new JdbcUserDetailsManager(
                        dataSource
                );

        userDetailsManager.setUsersByUsernameQuery(
                usersByEmailQuery
        );

        userDetailsManager
                .setAuthoritiesByUsernameQuery(
                        authoritiesByEmailQuery
                );

        return userDetailsManager;
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
                                        "/profile",
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
                                        HttpMethod.GET,
                                        "/vacancies/**"
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
                                .hasAuthority(
                                        "APPLICANT"
                                )

                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/resumes"
                                )
                                .hasAuthority(
                                        "EMPLOYER"
                                )

                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/resumes/create"
                                )
                                .hasAuthority(
                                        "APPLICANT"
                                )

                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/resumes/edit/**"
                                )
                                .hasAuthority(
                                        "APPLICANT"
                                )

                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/resumes/delete/**"
                                )
                                .hasAuthority(
                                        "APPLICANT"
                                )

                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/vacancies/create"
                                )
                                .hasAuthority(
                                        "EMPLOYER"
                                )

                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/vacancies/edit/**"
                                )
                                .hasAuthority(
                                        "EMPLOYER"
                                )

                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/vacancies/delete/**"
                                )
                                .hasAuthority(
                                        "EMPLOYER"
                                )

                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/responses/create"
                                )
                                .hasAuthority(
                                        "APPLICANT"
                                )

                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/responses/search/vacancy/**"
                                )
                                .hasAuthority(
                                        "EMPLOYER"
                                )

                                .anyRequest()
                                .authenticated()
                );

        return http.build();
    }
}