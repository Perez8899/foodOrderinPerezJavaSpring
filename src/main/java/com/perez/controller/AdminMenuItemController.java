package com.perez.controller;

import com.perez.Exception.FoodException;
import com.perez.Exception.RestaurantException;
import com.perez.Exception.UserException;
import com.perez.model.Food;
import com.perez.model.Restaurant;
import com.perez.model.User;
import com.perez.request.CreateFoodRequest;
import com.perez.service.CategoryService;
import com.perez.service.FoodService;
import com.perez.service.RestaurantService;
import com.perez.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/food")
public class AdminMenuItemController { //AdminFoodController

    @Autowired
    private FoodService menuItemService;
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<Food> createItem(
            @RequestBody CreateFoodRequest item,
            @RequestHeader("Authorization") String jwt)
            throws FoodException, UserException, RestaurantException {
        System.out.println("req-controller ----"+item);
        User user = userService.findUserProfileByJwt(jwt);
        Restaurant restaurant=restaurantService.findRestaurantById(item.getRestaurantId());
        Food menuItem = menuItemService.createFood(item,item.getCategory(),restaurant);
        return ResponseEntity.ok(menuItem);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id, @RequestHeader("Authorization") String jwt)
            throws UserException, FoodException {
        User user = userService.findUserProfileByJwt(jwt);

        menuItemService.deleteFood(id);
        return ResponseEntity.ok("Menu item deleted");
    }

    @GetMapping("/search")
    public ResponseEntity<List<Food>> getMenuItemByName(@RequestParam String name)  {
        List<Food> menuItem = menuItemService.searchFood(name);
        return ResponseEntity.ok(menuItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateAvilibilityStatus(
            @PathVariable Long id) throws FoodException {
        Food menuItems= menuItemService.updateAvailibilityStatus(id);
        return ResponseEntity.ok(menuItems);
    }
}
