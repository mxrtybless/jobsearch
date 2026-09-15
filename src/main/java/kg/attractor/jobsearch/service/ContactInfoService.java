package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ContactInfoDto;
import kg.attractor.jobsearch.model.Resume;

import java.util.List;

public interface ContactInfoService {

    List<ContactInfoDto> findByResumeId(
            Integer resumeId
    );

    void saveAll(
            Resume resume,
            List<ContactInfoDto> contactInfo
    );

    void replaceAll(
            Resume resume,
            List<ContactInfoDto> contactInfo
    );

    void deleteByResumeId(
            Integer resumeId
    );
}