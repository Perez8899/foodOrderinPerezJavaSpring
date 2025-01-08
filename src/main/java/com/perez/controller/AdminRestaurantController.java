package com.perez.controller;

import com.perez.Exception.RestaurantException;
import com.perez.Exception.UserException;
import com.perez.model.Restaurant;
import com.perez.model.User;
import com.perez.request.CreateRestaurantRequest;
import com.perez.response.ApiResponse;
import com.perez.service.RestaurantService;
import com.perez.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurants") //API

public class AdminRestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    //-------------------------http---------------------------------------------------------------
    @PostMapping()
    public ResponseEntity<Restaurant> createRestaurant(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader("Authorization") String jwt) throws UserException {

        //we search for the user by JWT
        User user = userService.findUserProfileByJwt(jwt);

        //here the restaurantte is created
        System.out.println("----TRUE___-----"+jwt);
        Restaurant restaurant = restaurantService.createRestaurant(req,user);
        return ResponseEntity.ok(restaurant);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long id, @RequestBody CreateRestaurantRequest req,
                                                       @RequestHeader("Authorization") String jwt) throws RestaurantException, UserException {
        User user = userService.findUserProfileByJwt(jwt);

        Restaurant restaurant = restaurantService.updateRestaurant(id, req);
        return ResponseEntity.ok(restaurant);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteRestaurantById(@PathVariable("id") Long restaurantId,
                                                            @RequestHeader("Authorization") String jwt) throws RestaurantException, UserException {
        User user = userService.findUserProfileByJwt(jwt);

        restaurantService.deleteRestaurant(restaurantId);

        ApiResponse res = new ApiResponse("Restaurant Deleted with id Successfully",true);
        return ResponseEntity.ok(res);
    }


    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateStataurantStatus(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id) throws RestaurantException, UserException {

        User user = userService.findUserProfileByJwt(jwt);
        Restaurant restaurant = restaurantService.updateRestaurantStatus(id);
        return ResponseEntity.ok(restaurant);

    }

    //ME DA ERROR CREO QUE ES POR DUPLICADO DEL OWNER ID
    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantsByUserId(
            @RequestHeader("Authorization") String jwt) throws RestaurantException, UserException {

        User user = userService.findUserProfileByJwt(jwt);
        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());

        return ResponseEntity.ok(restaurant);
    }

}
