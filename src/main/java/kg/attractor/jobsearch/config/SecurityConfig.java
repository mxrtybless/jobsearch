package kg.attractor.jobsearch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final DataSource dataSource;

    @Autowired
    public void configureGlobal(
            AuthenticationManagerBuilder auth
    ) throws Exception {
        String usersByEmailQuery = """
                SELECT
                    email AS username,
                    password,
                    TRUE AS enabled
                FROM users
                WHERE LOWER(email) = LOWER(?)
                """;

        String authoritiesByEmailQuery = """
                SELECT
                    email AS username,
                    account_type AS authority
                FROM users
                WHERE LOWER(email) = LOWER(?)
                """;

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder)
                .usersByUsernameQuery(
                        usersByEmailQuery
                )
                .authoritiesByUsernameQuery(
                        authoritiesByEmailQuery
                );
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http
    ) throws Exception {
        http
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )
                .httpBasic(
                        Customizer.withDefaults()
                )
                .formLogin(
                        AbstractHttpConfigurer::disable
                )
                .logout(
                        AbstractHttpConfigurer::disable
                )
                .csrf(
                        AbstractHttpConfigurer::disable
                )
                .authorizeHttpRequests(authorize ->
                        authorize
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
                                        "/v3/api-docs/**",
                                        "/error"
                                )
                                .permitAll()

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