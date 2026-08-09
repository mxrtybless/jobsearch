package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.model.Category;

import java.util.List;

public interface CategoryService {

    Category findById(Integer id);

    List<Category> findAll();
}