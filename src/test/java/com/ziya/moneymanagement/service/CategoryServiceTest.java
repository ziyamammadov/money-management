package com.ziya.moneymanagement.service;

import com.ziya.moneymanagement.entity.Category;
import com.ziya.moneymanagement.model.enums.CategoryType;
import com.ziya.moneymanagement.model.enums.Currency;
import com.ziya.moneymanagement.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @Mock
    private CategoryRepository repository;
    @InjectMocks
    private CategoryService service;
    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setCurrency(Currency.AZN);
        category.setDescription("Communal");
        category.setType(CategoryType.EXPENSE);
        category.setName("AzerIshiq");
    }

    @Test
    void shouldSavedUserSuccessFully() {
        given(repository.save(category)).willAnswer(invocation -> invocation.getArgument(0));

        Category savedCategory = service.save(this.category);

        assertThat(savedCategory).isNotNull();

        verify(repository).save(any(Category.class));
    }

    @Test
    void updateUser() {
        category.setId(1L);

        given(repository.findById(1L)).willReturn(Optional.of(category));
        given(repository.save(category)).willReturn(category);

        Category expected = service.update(this.category);

        assertThat(expected).isNotNull();

        verify(repository).save(any(Category.class));
    }

    @Test
    void shouldReturnFindAll() {
        List<Category> data = new ArrayList<>();
        data.add(category);

        given(repository.findAll()).willReturn(data);

        List<Category> expected = service.getAll();
        category.setId(1L);
        expected.add(category);

        assertEquals(expected, data);
    }

    @Test
    void findUserById() {
        final Long id = 1L;
        category.setId(id);
        given(repository.findById(id)).willReturn(Optional.of(category));

        Category expected = service.getOne(id);

        assertThat(expected).isNotNull();
    }

    @Test
    void shouldBeDelete() {
        final Long categoryId = 1L;
        given(repository.findById(categoryId)).willReturn(Optional.of(category));

        service.deleteById(categoryId);
        service.deleteById(categoryId);

        verify(repository, times(2)).deleteById(categoryId);
    }
}