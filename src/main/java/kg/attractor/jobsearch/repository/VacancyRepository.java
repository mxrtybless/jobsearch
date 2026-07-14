package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.Vacancy;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class VacancyRepository {
    private final Map<Integer, Vacancy> vacancies = new LinkedHashMap<>();
    private int nextId = 1;

    public List<Vacancy> findAll() {
        return new ArrayList<>(vacancies.values());
    }

    public Optional<Vacancy> findById(Integer id) {
        return Optional.ofNullable(vacancies.get(id));
    }

    public Vacancy save(Vacancy vacancy) {
        if (vacancy.getId() == null) {
            vacancy.setId(nextId++);
        }

        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }

    public void deleteById(Integer id) {
        vacancies.remove(id);
    }

    public boolean existsById(Integer id) {
        return vacancies.containsKey(id);
    }
}