package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.CategoryDao;
import kg.attractor.jobsearch.exception.CategoryNotFoundException;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl
        implements CategoryService {

    private final CategoryDao categoryDao;

    @Override
    public Category findById(Integer id) {
        return categoryDao.findById(id)
                .orElseThrow(() ->
                        new CategoryNotFoundException(
                                id
                        )
                );
    }

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }
}