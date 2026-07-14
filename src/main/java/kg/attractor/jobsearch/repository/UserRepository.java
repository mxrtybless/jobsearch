package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {
    private final Map<Integer, User> users = new LinkedHashMap<>();
    private int nextId = 1;

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public Optional<User> findById(Integer id) {
        return Optional.ofNullable(users.get(id));
    }

    public Optional<User> findByEmail(String email) {
        return users.values()
                .stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(nextId++);
        }

        users.put(user.getId(), user);
        return user;
    }

    public boolean existsByEmail(String email) {
        return findByEmail(email).isPresent();
    }
}