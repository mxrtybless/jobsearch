package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.ContactInfoDto;
import kg.attractor.jobsearch.exception.InvalidContactValueException;
import kg.attractor.jobsearch.exception.ResumeNotFoundException;
import kg.attractor.jobsearch.model.ContactInfo;
import kg.attractor.jobsearch.model.ContactType;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.repository.ContactInfoRepository;
import kg.attractor.jobsearch.repository.ResumeRepository;
import kg.attractor.jobsearch.service.ContactInfoService;
import kg.attractor.jobsearch.service.ContactTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactInfoServiceImpl
        implements ContactInfoService {

    private final ContactInfoRepository contactInfoRepository;
    private final ResumeRepository resumeRepository;
    private final ContactTypeService contactTypeService;

    @Override
    @Transactional(readOnly = true)
    public List<ContactInfoDto> findByResumeId(
            Integer resumeId
    ) {
        return contactInfoRepository
                .findByResume_Id(resumeId)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    @Transactional
    public void saveAll(
            Integer resumeId,
            List<ContactInfoDto> contactInfo
    ) {
        if (contactInfo == null) {
            return;
        }

        Resume resume = findResume(resumeId);

        for (ContactInfoDto contactDto
                : contactInfo) {

            if (contactDto == null
                    || contactDto.getValue() == null
                    || contactDto.getValue().isBlank()) {
                continue;
            }

            ContactType contactType =
                    contactTypeService.findById(
                            contactDto.getTypeId()
                    );

            validateContactValue(
                    contactType,
                    contactDto.getValue()
            );

            ContactInfo contact =
                    ContactInfo.builder()
                            .type(contactType)
                            .resume(resume)
                            .value(
                                    contactDto
                                            .getValue()
                                            .trim()
                            )
                            .build();

            contactInfoRepository.save(contact);
        }

        log.info(
                "Contact information saved for resume id: {}",
                resumeId
        );
    }

    @Override
    @Transactional
    public void replaceAll(
            Integer resumeId,
            List<ContactInfoDto> contactInfo
    ) {
        contactInfoRepository.deleteByResume_Id(
                resumeId
        );

        saveAll(
                resumeId,
                contactInfo
        );
    }

    @Override
    @Transactional
    public void deleteByResumeId(
            Integer resumeId
    ) {
        contactInfoRepository.deleteByResume_Id(
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

    private void validateContactValue(
            ContactType contactType,
            String value
    ) {
        String type = contactType.getType();
        boolean valid = true;

        if ("EMAIL".equals(type)) {
            valid = value.matches(
                    "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$"
            );
        } else if ("PHONE".equals(type)) {
            valid = value.matches(
                    "^\\+?[0-9]{7,15}$"
            );
        } else if ("TELEGRAM".equals(type)) {
            valid = value.matches(
                    "^@[A-Za-z0-9_]{5,32}$"
            );
        } else if ("FACEBOOK".equals(type)) {
            valid = value.matches(
                    "^https?://(www\\.)?facebook\\.com/.+$"
            );
        } else if ("LINKEDIN".equals(type)) {
            valid = value.matches(
                    "^https?://(www\\.)?linkedin\\.com/.+$"
            );
        }

        if (!valid) {
            throw new InvalidContactValueException(
                    type
            );
        }
    }

    private ContactInfoDto convertToDto(
            ContactInfo contactInfo
    ) {
        return new ContactInfoDto(
                contactInfo.getId(),
                contactInfo.getResume().getId(),
                contactInfo.getType().getId(),
                contactInfo.getValue()
        );
    }
}