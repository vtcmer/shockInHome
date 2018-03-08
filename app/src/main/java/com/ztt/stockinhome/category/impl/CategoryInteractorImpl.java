package com.ztt.stockinhome.category.impl;

import com.ztt.stockinhome.category.CategoryInteractor;
import com.ztt.stockinhome.category.CategoryRepository;
import com.ztt.stockinhome.category.model.Category;

import java.util.List;

/**
 * Created by vtcmer on 04/09/2016.
 */
public class CategoryInteractorImpl implements CategoryInteractor {

    private CategoryRepository repository;

    public CategoryInteractorImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(Category category) {
        this.repository.create(category);
    }

    @Override
    public void update(Category category) {
        this.repository.update(category);
    }

    @Override
    public void delete(Long id) {
        this.repository.delete(id);
    }

    @Override
    public void deleteItems(List<Category> categories) {
        this.repository.deleteItems(categories);
    }

    @Override
    public void get(Long id) {
        this.repository.get(id);
    }

    @Override
    public void findAll() {
        this.repository.findAll();
    }

    @Override
    public boolean exist(Category category) {
        return this.repository.exist(category);
    }
}
