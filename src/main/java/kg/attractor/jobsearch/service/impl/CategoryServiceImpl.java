package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.exception.CategoryNotFoundException;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.repository.CategoryRepository;
import kg.attractor.jobsearch.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl
        implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public Category findById(Integer id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() ->
                        new CategoryNotFoundException(
                                id
                        )
                );
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}