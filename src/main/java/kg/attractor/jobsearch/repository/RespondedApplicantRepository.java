package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.RespondedApplicant;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class RespondedApplicantRepository {
    private final Map<Integer, RespondedApplicant> respondedApplicants = new LinkedHashMap<>();
    private int nextId = 1;

    public List<RespondedApplicant> findAll() {
        return new ArrayList<>(respondedApplicants.values());
    }

    public Optional<RespondedApplicant> findById(Integer id) {
        return Optional.ofNullable(respondedApplicants.get(id));
    }

    public List<RespondedApplicant> findByVacancyId(Integer vacancyId) {
        return respondedApplicants.values()
                .stream()
                .filter(response -> response.getVacancyId().equals(vacancyId))
                .toList();
    }

    public RespondedApplicant save(RespondedApplicant respondedApplicant) {
        if (respondedApplicant.getId() == null) {
            respondedApplicant.setId(nextId++);
        }

        respondedApplicants.put(respondedApplicant.getId(), respondedApplicant);
        return respondedApplicant;
    }

    public boolean existsByResumeIdAndVacancyId(Integer resumeId, Integer vacancyId) {
        return respondedApplicants.values()
                .stream()
                .anyMatch(response ->
                        response.getResumeId().equals(resumeId)
                                && response.getVacancyId().equals(vacancyId)
                );
    }
}