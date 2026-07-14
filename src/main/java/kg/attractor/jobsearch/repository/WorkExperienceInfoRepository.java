package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.WorkExperienceInfo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class WorkExperienceInfoRepository {
    private final Map<Integer, WorkExperienceInfo> workExperiences = new LinkedHashMap<>();
    private int nextId = 1;

    public List<WorkExperienceInfo> findByResumeId(Integer resumeId) {
        return workExperiences.values()
                .stream()
                .filter(workExperience -> workExperience.getResumeId().equals(resumeId))
                .toList();
    }

    public WorkExperienceInfo save(WorkExperienceInfo workExperienceInfo) {
        if (workExperienceInfo.getId() == null) {
            workExperienceInfo.setId(nextId++);
        }

        workExperiences.put(workExperienceInfo.getId(), workExperienceInfo);
        return workExperienceInfo;
    }

    public List<WorkExperienceInfo> saveAll(List<WorkExperienceInfo> workExperienceInfos) {
        List<WorkExperienceInfo> savedWorkExperiences = new ArrayList<>();

        for (WorkExperienceInfo workExperienceInfo : workExperienceInfos) {
            savedWorkExperiences.add(save(workExperienceInfo));
        }

        return savedWorkExperiences;
    }

    public void deleteByResumeId(Integer resumeId) {
        workExperiences.values().removeIf(workExperience -> workExperience.getResumeId().equals(resumeId));
    }
}