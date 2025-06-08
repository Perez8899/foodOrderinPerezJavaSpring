package com.perez.controller;

import com.perez.Exception.RestaurantException;
import com.perez.Exception.UserException;
import com.perez.model.Category;
import com.perez.model.User;
import com.perez.service.CategoryService;
import com.perez.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    public CategoryService categoryService;
    @Autowired
    public UserService userService;

    @PostMapping("/admin/category") //for owner restaurant
    public ResponseEntity<Category> createdCategory( @RequestBody Category category,
            @RequestHeader("Authorization")String jwt) throws RestaurantException, UserException {

        User user=userService.findUserProfileByJwt(jwt);
        Category createdCategory=categoryService.createCategory(category.getName(), user.getId());

        return ResponseEntity.ok(createdCategory);
    }

    @GetMapping("/category/restaurant/{id}")   //endpoint for customers
    public ResponseEntity<List<Category>> getRestaurantsCategory(
            @PathVariable Long id,
            @RequestHeader("Authorization")String jwt) throws RestaurantException, UserException {

        User user=userService.findUserProfileByJwt(jwt);
        List<Category> categories=categoryService.findCategoryByRestaurantId(id);
        return new ResponseEntity<>(categories,HttpStatus.OK);
    }
}
