package com.ziya.moneymanagement.service;

import com.ziya.moneymanagement.entity.Account;
import com.ziya.moneymanagement.entity.Category;
import com.ziya.moneymanagement.exception.CategoryNotFoundException;
import com.ziya.moneymanagement.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements CrudService<Category>{
    private final CategoryRepository categoryRepository;

    @Cacheable(value = "allCategories", unless = "#result == null")
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Cacheable(value = "allCategories", unless = "#result == null", key = "#id")
    public Category getOne(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);
    }

    @CachePut(value = "allCategories", key = "#category.id")
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @CacheEvict(value = "allCategories", key = "#result.id")
    public Category update(Category category) {
        Long id = category.getId();
        return categoryRepository.findById(id)
                .map(cat -> categoryRepository.save(category))
                .orElseThrow(CategoryNotFoundException::new);
    }

    @CacheEvict(value = "allCategories", key = "#id")
    public Category deleteById(Long id) {
        return categoryRepository.findById(id)
                .map(cat -> {
                    categoryRepository.deleteById(id);
                    return cat;
                })
                .orElseThrow(CategoryNotFoundException::new);
    }
}
