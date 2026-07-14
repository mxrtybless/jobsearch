package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.EducationInfo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EducationInfoRepository {
    private final Map<Integer, EducationInfo> educations = new LinkedHashMap<>();
    private int nextId = 1;

    public List<EducationInfo> findByResumeId(Integer resumeId) {
        return educations.values()
                .stream()
                .filter(education -> education.getResumeId().equals(resumeId))
                .toList();
    }

    public EducationInfo save(EducationInfo educationInfo) {
        if (educationInfo.getId() == null) {
            educationInfo.setId(nextId++);
        }

        educations.put(educationInfo.getId(), educationInfo);
        return educationInfo;
    }

    public List<EducationInfo> saveAll(List<EducationInfo> educationInfos) {
        List<EducationInfo> savedEducations = new ArrayList<>();

        for (EducationInfo educationInfo : educationInfos) {
            savedEducations.add(save(educationInfo));
        }

        return savedEducations;
    }

    public void deleteByResumeId(Integer resumeId) {
        educations.values().removeIf(education -> education.getResumeId().equals(resumeId));
    }
}