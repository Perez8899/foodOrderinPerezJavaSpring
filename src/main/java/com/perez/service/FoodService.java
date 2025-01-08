package com.perez.service;

import com.perez.Exception.FoodException;
import com.perez.Exception.RestaurantException;
import com.perez.model.Category;
import com.perez.model.Food;
import com.perez.model.Restaurant;
import com.perez.request.CreateFoodRequest;

import java.util.List;

public interface FoodService { //All Methods and Public
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant)  throws FoodException, RestaurantException;

                               //restaurant owner can remove food
    void deleteFood(Long foodId) throws FoodException;

    public List<Food> getRestaurantsFood(Long restaurantId,
                                         boolean isVegetarian, boolean isNonveg, boolean isSeasonal, String foodCategory) throws FoodException;

    public List<Food> searchFood(String keyword);

    public Food findFoodById(Long foodId) throws FoodException;

    //availability of food or not n stock
    public Food updateAvailibilityStatus(Long foodId) throws FoodException;
}
