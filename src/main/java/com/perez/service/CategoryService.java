package com.perez.service;

import com.perez.Exception.RestaurantException;
import com.perez.model.Category;

import java.util.List;

public interface CategoryService{ //All Methods and Public

    public Category createCategory (String name, Long userId) throws RestaurantException;
    public List<Category> findCategoryByRestaurantId(Long restaurantId) throws RestaurantException;
    public Category findCategoryById(Long id) throws RestaurantException;

}
