package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.WorkExperienceInfoDto;
import kg.attractor.jobsearch.exception.ResumeNotFoundException;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.WorkExperienceInfo;
import kg.attractor.jobsearch.repository.ResumeRepository;
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
    private final ResumeRepository resumeRepository;

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
            Integer resumeId,
            List<WorkExperienceInfoDto> workExperienceInfo
    ) {
        saveRecords(
                resumeId,
                workExperienceInfo
        );
    }

    @Override
    @Transactional
    public void replaceAll(
            Integer resumeId,
            List<WorkExperienceInfoDto> workExperienceInfo
    ) {
        workExperienceInfoRepository
                .deleteByResume_Id(
                        resumeId
                );

        saveRecords(
                resumeId,
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
            Integer resumeId,
            List<WorkExperienceInfoDto> workExperienceInfo
    ) {
        if (workExperienceInfo == null) {
            return;
        }

        Resume resume = findResume(resumeId);

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
                resumeId
        );
    }

    private Resume findResume(Integer resumeId) {
        return resumeRepository
                .findById(resumeId)
                .orElseThrow(() ->
                        new ResumeNotFoundException(
                                resumeId
                        )
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