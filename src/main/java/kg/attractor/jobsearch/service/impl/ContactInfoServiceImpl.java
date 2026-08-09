package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.ContactInfoDao;
import kg.attractor.jobsearch.dto.ContactInfoDto;
import kg.attractor.jobsearch.exception.InvalidContactValueException;
import kg.attractor.jobsearch.model.ContactInfo;
import kg.attractor.jobsearch.model.ContactType;
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

    private final ContactInfoDao contactInfoDao;
    private final ContactTypeService
            contactTypeService;

    @Override
    public List<ContactInfoDto> findByResumeId(
            Integer resumeId
    ) {
        return contactInfoDao
                .findByResumeId(resumeId)
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

        for (ContactInfoDto contactDto
                : contactInfo) {

            if (contactDto == null
                    || contactDto.getValue() == null
                    || contactDto.getValue()
                    .isBlank()) {
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
                            .typeId(
                                    contactDto
                                            .getTypeId()
                            )
                            .resumeId(
                                    resumeId
                            )
                            .value(
                                    contactDto
                                            .getValue()
                                            .trim()
                            )
                            .build();

            contactInfoDao.save(contact);
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
        contactInfoDao.deleteByResumeId(
                resumeId
        );

        saveAll(
                resumeId,
                contactInfo
        );
    }

    @Override
    public void deleteByResumeId(
            Integer resumeId
    ) {
        contactInfoDao.deleteByResumeId(
                resumeId
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
                contactInfo.getResumeId(),
                contactInfo.getTypeId(),
                contactInfo.getValue()
        );
    }
}