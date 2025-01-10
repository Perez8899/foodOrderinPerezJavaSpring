package com.perez.service;

import com.perez.Exception.RestaurantException;
import com.perez.model.IngredientCategory;
import com.perez.model.IngredientsItem;
import com.perez.model.Restaurant;
import com.perez.reporsitory.IngredientCategoryRepository;
import com.perez.reporsitory.IngredientItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImplementation implements IngredientsService{

    @Autowired
    private IngredientCategoryRepository ingredientsCategoryRepo;
    @Autowired
    private IngredientItemRepository ingredientsItemRepository;
    @Autowired
    private RestaurantService restaurantService;

    @Override
    public IngredientCategory createIngredientsCategory(
            String name,Long restaurantId) throws RestaurantException {

        IngredientCategory isExist=ingredientsCategoryRepo
                .findByRestaurantIdAndNameIgnoreCase(restaurantId,name);

        if(isExist!=null) {
            return isExist;
        }

        Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);

        IngredientCategory ingredientCategory=new IngredientCategory();
        ingredientCategory.setRestaurant(restaurant);
        ingredientCategory.setName(name);

        return ingredientsCategoryRepo.save(ingredientCategory);
    }

    @Override
    public IngredientCategory findIngredientsCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> opt=ingredientsCategoryRepo.findById(id);
        if(opt.isEmpty()){
            throw new Exception("ingredient category not found");
        }
        return opt.get();
    }

    @Override
    public List<IngredientCategory> findIngredientsCategoryByRestaurantId(Long id) throws Exception {
        return ingredientsCategoryRepo.findByRestaurantId(id);
    }

    @Override
    public List<IngredientsItem> findRestaurantsIngredients(Long restaurantId) {

        return ingredientsItemRepository.findByRestaurantId(restaurantId);
    }


    @Override
    public IngredientsItem createIngredientsItem(Long restaurantId,
                                                 String ingredientName,
                                                 Long ingredientCategoryId) throws Exception {

        IngredientCategory category = findIngredientsCategoryById(ingredientCategoryId);

        IngredientsItem isExist = ingredientsItemRepository.
                findByRestaurantIdAndNameIngoreCase(restaurantId, ingredientName,category.getName());
        if(isExist!=null) {
            System.out.println("is exists-------- item");
            return isExist;
        }

        Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);

        IngredientsItem item=new IngredientsItem();
        item.setName(ingredientName);
        item.setRestaurant(restaurant);
        item.setCategory(category);

        IngredientsItem savedIngredients = ingredientsItemRepository.save(item);
        category.getIngredients().add(savedIngredients);

        return savedIngredients;
    }


    @Override
    public IngredientsItem updateStoke(Long id) throws Exception {
        //Opcional--JPA method, the ingredient may or may not be
        Optional<IngredientsItem> item=ingredientsItemRepository.findById(id);
        if(item.isEmpty()) {
            throw new Exception("ingredient not found with id "+item);
        }
                                        //we get the value obtained
        IngredientsItem ingredient=item.get();
        ingredient.setInStoke(!ingredient.isInStoke());

        return ingredientsItemRepository.save(ingredient);
    }
}
