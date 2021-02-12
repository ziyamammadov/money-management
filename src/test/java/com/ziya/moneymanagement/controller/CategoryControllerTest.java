package com.ziya.moneymanagement.controller;

import com.ziya.moneymanagement.entity.Category;
import com.ziya.moneymanagement.model.enums.CategoryType;
import com.ziya.moneymanagement.model.enums.Currency;
import com.ziya.moneymanagement.exception.CategoryNotFoundException;
import com.ziya.moneymanagement.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryController.class)
class CategoryControllerTest {
    private Category category;
    private List<Category> categoryList;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService service;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setCurrency(Currency.AZN);
        category.setDescription("Communal");
        category.setType(CategoryType.EXPENSE);
        category.setName("AzerIshiq");
        categoryList = new ArrayList<>();
        categoryList.add(category);
    }

    @Test
    void shouldFetchAllUsers() throws Exception {

        given(service.getAll()).willReturn(categoryList);

        this.mockMvc.perform(get("/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(categoryList.size())));
    }

    @Test
    void shouldFetchOneUserById() throws Exception {
        final Long categoryId = 1L;
        category.setId(categoryId);

        given(service.getOne(categoryId)).willReturn(category);

        this.mockMvc.perform(get("/category/{id}", categoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currency", is(category.getCurrency().toString())))
                .andExpect(jsonPath("$.description", is(category.getDescription())))
                .andExpect(jsonPath("$.type", is(category.getType().toString())))
                .andExpect(jsonPath("$.name", is(category.getName())));
    }

    @Test
    void shouldReturnExceptionWhenFindUserById() throws Exception {
        final Long categoryId = 1L;
        given(service.getOne(categoryId)).willThrow(new CategoryNotFoundException());

        this.mockMvc.perform(get("/category/{id}", categoryId))
                .andExpect(jsonPath("$.code", is(404)))
                .andExpect(jsonPath("$.description", is("Category not found")));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        Long categoryId = 1L;
        category.setId(categoryId);
        given(service.deleteById(categoryId)).willReturn(category);

        this.mockMvc.perform(delete("/category/{id}", category.getId()))
                .andExpect(status().isOk());

    }

}