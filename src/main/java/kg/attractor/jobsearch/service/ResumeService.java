package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.model.Resume;

import java.util.List;

public interface ResumeService {
    void createResume(Resume resume);

    void editResume(
            Integer id,
            Resume resume
    );

    void deleteResume(Integer id);

    List<Resume> findAllActive();

    List<Resume> findByCategoryId(
            Integer categoryId
    );

    List<Resume> findByApplicantId(
            Integer applicantId
    );
}