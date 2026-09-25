package kg.attractor.jobsearch.service;
public interface PasswordRecoveryService {
    String issue(String email);
    boolean valid(String token);
    boolean reset(String token, String password);
}
