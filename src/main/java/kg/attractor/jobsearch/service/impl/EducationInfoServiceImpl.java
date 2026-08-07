package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.EducationInfoDao;
import kg.attractor.jobsearch.dto.EducationInfoDto;
import kg.attractor.jobsearch.exception.InvalidEducationPeriodException;
import kg.attractor.jobsearch.model.EducationInfo;
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

    private final EducationInfoDao
            educationInfoDao;

    @Override
    public List<EducationInfoDto> findByResumeId(
            Integer resumeId
    ) {
        return educationInfoDao
                .findByResumeId(resumeId)
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

        educationInfoDao.deleteByResumeId(
                resumeId
        );

        saveRecords(
                resumeId,
                educationInfo
        );
    }

    @Override
    public void deleteByResumeId(
            Integer resumeId
    ) {
        educationInfoDao.deleteByResumeId(
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

        for (EducationInfoDto educationDto
                : educationInfo) {

            EducationInfo education =
                    EducationInfo.builder()
                            .resumeId(resumeId)
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

            educationInfoDao.save(
                    education
            );
        }

        log.info(
                "Education records saved for resume id: {}",
                resumeId
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
                educationInfo.getResumeId(),
                educationInfo.getInstitution(),
                educationInfo.getProgram(),
                educationInfo.getStartDate(),
                educationInfo.getEndDate(),
                educationInfo.getDegree()
        );
    }
}