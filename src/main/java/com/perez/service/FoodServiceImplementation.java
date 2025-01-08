package com.perez.service;

import com.perez.Exception.FoodException;
import com.perez.Exception.RestaurantException;
import com.perez.model.Category;
import com.perez.model.Food;
import com.perez.model.Restaurant;
import com.perez.reporsitory.FoodRepository;
import com.perez.request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodServiceImplementation implements FoodService{ //implements the interface methods


    @Autowired
    private FoodRepository foodRepository;
//	@Autowired
//	private RestaurantRepository restaurantRepository;

  //  @Autowired
    //private IngredientsService ingredientService;


   // @Autowired
   // private IngredientsCategoryRepository ingredientCategoryRepo;

    //--------------methods----------------------------------------------------------------------
    @Override
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) throws FoodException, RestaurantException {
        return null;
    }

    @Override
    public void deleteFood(Long foodId) throws FoodException {

    }

    @Override
    public List<Food> getRestaurantsFood(Long restaurantId, boolean isVegetarian, boolean isNonveg, boolean isSeasonal, String foodCategory) throws FoodException {
        return null;
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return null;
    }

    @Override
    public Food findFoodById(Long foodId) throws FoodException {
        return null;
    }

    @Override
    public Food updateAvailibilityStatus(Long foodId) throws FoodException {
        return null;
    }
}
