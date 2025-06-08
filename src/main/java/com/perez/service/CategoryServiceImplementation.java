package com.perez.service;

import com.perez.Exception.RestaurantException;
import com.perez.model.Category;
import com.perez.model.Restaurant;
import com.perez.reporsitory.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategoryServiceImplementation implements CategoryService{

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private CategoryRepository categoryRepository;

    //-----------------methods---------------------------------------------------------------------
    @Override
    public Category createCategory(String name,Long userId) throws RestaurantException {
        Restaurant restaurant=restaurantService.getRestaurantByUserId(userId);
        if(restaurant== null){
            throw new RestaurantException("User has no restaurant assined");
        }

        Category createdCategory=new Category();
        createdCategory.setName(name);
        createdCategory.setRestaurant(restaurant);

        if(categoryRepository.existsByNameAndRestaurantId(name, restaurant.getId())){
            throw  new RestaurantException("Category name alredy xist for this Restaurant");

        }
        return categoryRepository.save(createdCategory);
    }

    @Override
    public List<Category> findCategoryByRestaurantId(Long id) throws RestaurantException {
        Restaurant restaurant=restaurantService.findRestaurantById(id);
        return categoryRepository.findByRestaurantId(id);
    }

    @Override
    public Category findCategoryById(Long id) throws RestaurantException {
        Optional<Category> opt=categoryRepository.findById(id);

        if(opt.isEmpty()) {
            throw new RestaurantException("category not exist with id "+id);
        }

        return opt.get();
    }

}
