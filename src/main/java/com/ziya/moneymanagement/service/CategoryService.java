package com.ziya.moneymanagement.service;

import com.ziya.moneymanagement.entity.Category;
import com.ziya.moneymanagement.exception.CategoryNotFoundException;
import com.ziya.moneymanagement.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category getOne(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category update(Category category) {
        Long id = category.getId();
        return categoryRepository.findById(id)
                .map(cat -> categoryRepository.save(category))
                .orElseThrow(CategoryNotFoundException::new);
    }

    public Category deleteById(Long id) {
        return categoryRepository.findById(id)
                .map(cat -> {
                    categoryRepository.deleteById(id);
                    return cat;
                })
                .orElseThrow(CategoryNotFoundException::new);
    }
}
