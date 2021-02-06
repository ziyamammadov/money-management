package com.ziya.moneymanagement.entity;

import com.ziya.moneymanagement .entity.enums.CategoryType;
import com.ziya.moneymanagement.entity.enums.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryTest {
    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setCurrency(Currency.AZN);
        category.setDescription("Communal");
        category.setType(CategoryType.EXPENSE);
        category.setName("AzerIshiq");
        category.setCategoryBalance(5000);
    }

    @Test
    void dependentAssertions() {
        assertAll("Properties Test",
                () -> assertEquals(Currency.AZN, category.getCurrency(), "Category currency did not match"),
                () -> assertEquals("Communal", category.getDescription(), "Category description did not match"),
                () -> assertEquals(CategoryType.EXPENSE, category.getType(), "Category type did not match"),
                () -> assertEquals("AzerIshiq", category.getName(), "Category name did not match"),
                () -> assertEquals(5000, category.getCategoryBalance(), "Category balance did not match"));
    }

}