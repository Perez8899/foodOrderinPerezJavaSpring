package com.perez.controller;

import com.perez.Exception.CartException;
import com.perez.Exception.OrderException;
import com.perez.Exception.RestaurantException;
import com.perez.Exception.UserException;
import com.perez.model.Order;
import com.perez.model.PaymentResponse;
import com.perez.model.User;
import com.perez.request.CreateOrderRequest;
import com.perez.service.OrderService;
import com.perez.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {     //----users---------

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    //----------------methods----------------------------------------------------------------------
    @PostMapping("/order")
    //public ResponseEntity<PaymentResponse> createOrder(@RequestBody CreateOrderRequest order,
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest req,
                                                       @RequestHeader("Authorization") String jwt)
            throws UserException, RestaurantException,
            CartException,
            //StripeException,
            OrderException {

        User user = userService.findUserProfileByJwt(jwt);
        System.out.println("req user " + user.getEmail());
        if (req != null) {
            //PaymentResponse res = orderService.createOrder(order, user);
            Order order = orderService.createOrder(req, user);
            return ResponseEntity.ok(order);

        } else throw new OrderException("Please provide valid request body");

    }


    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getAllUserOrders(@RequestHeader("Authorization") String jwt) throws OrderException, UserException {

        User user = userService.findUserProfileByJwt(jwt);

        if (user.getId() != null) {
            List<Order> userOrders = orderService.getUserOrders(user.getId());
            return ResponseEntity.ok(userOrders);
        } else {
            return new ResponseEntity<List<Order>>(HttpStatus.BAD_REQUEST);
        }
    }
}
