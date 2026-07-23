package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.ResumeDao;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl
        implements ResumeService {

    private final ResumeDao resumeDao;

    @Override
    public void createResume(Resume resume) {
        LocalDateTime now =
                LocalDateTime.now();

        resume.setIsActive(true);
        resume.setCreatedDate(now);
        resume.setUpdateTime(now);

        resumeDao.save(resume);
    }

    @Override
    public void editResume(
            Integer id,
            Resume resume
    ) {
        Resume savedResume =
                resumeDao.findById(id)
                        .orElseThrow();

        savedResume.setName(
                resume.getName()
        );

        savedResume.setCategoryId(
                resume.getCategoryId()
        );

        savedResume.setSalary(
                resume.getSalary()
        );

        if (resume.getIsActive() != null) {
            savedResume.setIsActive(
                    resume.getIsActive()
            );
        }

        savedResume.setUpdateTime(
                LocalDateTime.now()
        );

        resumeDao.update(savedResume);
    }

    @Override
    public void deleteResume(Integer id) {
        resumeDao.deleteById(id);
    }

    @Override
    public List<Resume> findAllActive() {
        return resumeDao.findAllActive();
    }

    @Override
    public List<Resume> findByCategoryId(
            Integer categoryId
    ) {
        return resumeDao.findByCategoryId(
                categoryId
        );
    }

    @Override
    public List<Resume> findByApplicantId(
            Integer applicantId
    ) {
        return resumeDao.findByApplicantId(
                applicantId
        );
    }
}