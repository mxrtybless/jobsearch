package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.EducationInfoDto;
import kg.attractor.jobsearch.exception.InvalidEducationPeriodException;
import kg.attractor.jobsearch.exception.ResumeNotFoundException;
import kg.attractor.jobsearch.model.EducationInfo;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.repository.EducationInfoRepository;
import kg.attractor.jobsearch.repository.ResumeRepository;
import kg.attractor.jobsearch.service.EducationInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EducationInfoServiceImpl
        implements EducationInfoService {

    private final EducationInfoRepository educationInfoRepository;
    private final ResumeRepository resumeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EducationInfoDto> findByResumeId(
            Integer resumeId
    ) {
        return educationInfoRepository
                .findByResume_Id(resumeId)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    @Transactional
    public void saveAll(
            Integer resumeId,
            List<EducationInfoDto> educationInfo
    ) {
        validatePeriods(educationInfo);
        saveRecords(
                resumeId,
                educationInfo
        );
    }

    @Override
    @Transactional
    public void replaceAll(
            Integer resumeId,
            List<EducationInfoDto> educationInfo
    ) {
        validatePeriods(educationInfo);

        educationInfoRepository.deleteByResume_Id(
                resumeId
        );

        saveRecords(
                resumeId,
                educationInfo
        );
    }

    @Override
    @Transactional
    public void deleteByResumeId(
            Integer resumeId
    ) {
        educationInfoRepository.deleteByResume_Id(
                resumeId
        );
    }

    private void saveRecords(
            Integer resumeId,
            List<EducationInfoDto> educationInfo
    ) {
        if (educationInfo == null) {
            return;
        }

        Resume resume = findResume(resumeId);

        for (EducationInfoDto educationDto
                : educationInfo) {

            if (educationDto == null) {
                continue;
            }

            EducationInfo education =
                    EducationInfo.builder()
                            .resume(resume)
                            .institution(
                                    educationDto
                                            .getInstitution()
                            )
                            .program(
                                    educationDto
                                            .getProgram()
                            )
                            .startDate(
                                    educationDto
                                            .getStartDate()
                            )
                            .endDate(
                                    educationDto
                                            .getEndDate()
                            )
                            .degree(
                                    educationDto
                                            .getDegree()
                            )
                            .build();

            educationInfoRepository.save(
                    education
            );
        }

        log.info(
                "Education records saved for resume id: {}",
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

    private void validatePeriods(
            List<EducationInfoDto> educationInfo
    ) {
        if (educationInfo == null) {
            return;
        }

        for (EducationInfoDto educationDto
                : educationInfo) {

            if (educationDto == null) {
                continue;
            }

            if (educationDto.getStartDate()
                    != null
                    && educationDto.getEndDate()
                    != null
                    && educationDto.getEndDate()
                    .isBefore(
                            educationDto
                                    .getStartDate()
                    )) {

                throw new InvalidEducationPeriodException(
                        educationDto
                                .getInstitution()
                );
            }
        }
    }

    private EducationInfoDto convertToDto(
            EducationInfo educationInfo
    ) {
        return new EducationInfoDto(
                educationInfo.getId(),
                educationInfo.getResume().getId(),
                educationInfo.getInstitution(),
                educationInfo.getProgram(),
                educationInfo.getStartDate(),
                educationInfo.getEndDate(),
                educationInfo.getDegree()
        );
    }
}