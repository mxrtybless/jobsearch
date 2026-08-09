package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.ContactTypeDao;
import kg.attractor.jobsearch.exception.ContactTypeNotFoundException;
import kg.attractor.jobsearch.model.ContactType;
import kg.attractor.jobsearch.service.ContactTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactTypeServiceImpl
        implements ContactTypeService {

    private final ContactTypeDao contactTypeDao;

    @Override
    public ContactType findById(Integer id) {
        return contactTypeDao.findById(id)
                .orElseThrow(() ->
                        new ContactTypeNotFoundException(
                                id
                        )
                );
    }

    @Override
    public List<ContactType> findAll() {
        return contactTypeDao.findAll();
    }
}