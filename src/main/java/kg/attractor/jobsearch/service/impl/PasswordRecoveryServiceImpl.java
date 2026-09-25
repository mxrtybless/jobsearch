package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.repository.UserRepository;
import kg.attractor.jobsearch.service.PasswordRecoveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordRecoveryServiceImpl implements PasswordRecoveryService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public String issue(String email) {
        var found = userRepository.findByEmailIgnoreCase(email.trim());
        if (found.isEmpty()) return null;
        var user = found.get();
        String token = UUID.randomUUID().toString();
        user.setResetPasswordToken(token);
        user.setResetPasswordExpiresAt(LocalDateTime.now().plusMinutes(30));
        userRepository.save(user);
        return token;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean valid(String token) {
        if (token == null || token.isBlank() || token.length() > 255) return false;
        return userRepository.findByResetPasswordToken(token)
                .filter(user -> user.getResetPasswordExpiresAt() != null
                        && user.getResetPasswordExpiresAt().isAfter(LocalDateTime.now()))
                .isPresent();
    }

    @Override
    @Transactional
    public boolean reset(String token, String password) {
        if (token == null || token.isBlank() || token.length() > 255) return false;
        if (password == null || password.length() < 8 || password.length() > 32
                || !password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])\\S+$")) return false;
        return userRepository.consumeResetToken(token, passwordEncoder.encode(password), LocalDateTime.now()) == 1;
    }
}
