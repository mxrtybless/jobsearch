package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.exception.ContactTypeNotFoundException;
import kg.attractor.jobsearch.model.ContactType;
import kg.attractor.jobsearch.repository.ContactTypeRepository;
import kg.attractor.jobsearch.service.ContactTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactTypeServiceImpl
        implements ContactTypeService {

    private final ContactTypeRepository contactTypeRepository;

    @Override
    @Transactional(readOnly = true)
    public ContactType findById(Integer id) {
        return contactTypeRepository
                .findById(id)
                .orElseThrow(() ->
                        new ContactTypeNotFoundException(
                                id
                        )
                );
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactType> findAll() {
        return contactTypeRepository.findAll();
    }
}