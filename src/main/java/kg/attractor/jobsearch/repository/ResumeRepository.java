package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.Resume;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ResumeRepository {
    private final Map<Integer, Resume> resumes = new LinkedHashMap<>();
    private int nextId = 1;

    public List<Resume> findAll() {
        return new ArrayList<>(resumes.values());
    }

    public Optional<Resume> findById(Integer id) {
        return Optional.ofNullable(resumes.get(id));
    }

    public Resume save(Resume resume) {
        if (resume.getId() == null) {
            resume.setId(nextId++);
        }

        resumes.put(resume.getId(), resume);
        return resume;
    }

    public void deleteById(Integer id) {
        resumes.remove(id);
    }
}