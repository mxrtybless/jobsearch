package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.Category;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CategoryRepository {
    private final Map<Integer, Category> categories = new LinkedHashMap<>();

    public CategoryRepository() {
        save(new Category(1, "IT", null));
        save(new Category(2, "Marketing", null));
        save(new Category(3, "Sales", null));
        save(new Category(4, "Design", null));
        save(new Category(5, "Finance", null));
    }

    public List<Category> findAll() {
        return new ArrayList<>(categories.values());
    }

    public Optional<Category> findById(Integer id) {
        return Optional.ofNullable(categories.get(id));
    }

    private void save(Category category) {
        categories.put(category.getId(), category);
    }
}