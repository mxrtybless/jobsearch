package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ContactInfoDto;

import java.util.List;

public interface ContactInfoService {

    List<ContactInfoDto> findByResumeId(
            Integer resumeId
    );

    void saveAll(
            Integer resumeId,
            List<ContactInfoDto> contactInfo
    );

    void replaceAll(
            Integer resumeId,
            List<ContactInfoDto> contactInfo
    );

    void deleteByResumeId(
            Integer resumeId
    );
}