package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.WorkExperienceInfoDto;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.WorkExperienceInfo;
import kg.attractor.jobsearch.repository.WorkExperienceInfoRepository;
import kg.attractor.jobsearch.service.WorkExperienceInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkExperienceInfoServiceImpl
        implements WorkExperienceInfoService {

    private final WorkExperienceInfoRepository workExperienceInfoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<WorkExperienceInfoDto>
    findByResumeId(
            Integer resumeId
    ) {
        return workExperienceInfoRepository
                .findByResume_Id(resumeId)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    @Transactional
    public void saveAll(
            Resume resume,
            List<WorkExperienceInfoDto> workExperienceInfo
    ) {
        saveRecords(
                resume,
                workExperienceInfo
        );
    }

    @Override
    @Transactional
    public void replaceAll(
            Resume resume,
            List<WorkExperienceInfoDto> workExperienceInfo
    ) {
        workExperienceInfoRepository
                .deleteByResume_Id(
                        resume.getId()
                );

        saveRecords(
                resume,
                workExperienceInfo
        );
    }

    @Override
    @Transactional
    public void deleteByResumeId(
            Integer resumeId
    ) {
        workExperienceInfoRepository
                .deleteByResume_Id(
                        resumeId
                );
    }

    private void saveRecords(
            Resume resume,
            List<WorkExperienceInfoDto> workExperienceInfo
    ) {
        if (workExperienceInfo == null) {
            return;
        }

        for (WorkExperienceInfoDto workDto
                : workExperienceInfo) {

            if (workDto == null) {
                continue;
            }

            WorkExperienceInfo workExperience =
                    WorkExperienceInfo.builder()
                            .resume(resume)
                            .years(
                                    workDto.getYears()
                            )
                            .companyName(
                                    workDto
                                            .getCompanyName()
                            )
                            .position(
                                    workDto
                                            .getPosition()
                            )
                            .responsibilities(
                                    workDto
                                            .getResponsibilities()
                            )
                            .build();

            workExperienceInfoRepository.save(
                    workExperience
            );
        }

        log.info(
                "Work experience records saved for resume id: {}",
                resume.getId()
        );
    }

    private WorkExperienceInfoDto convertToDto(
            WorkExperienceInfo workExperience
    ) {
        return new WorkExperienceInfoDto(
                workExperience.getId(),
                workExperience.getResume().getId(),
                workExperience.getYears(),
                workExperience.getCompanyName(),
                workExperience.getPosition(),
                workExperience.getResponsibilities()
        );
    }
}