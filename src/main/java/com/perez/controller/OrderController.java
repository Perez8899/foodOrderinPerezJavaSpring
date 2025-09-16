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
import com.perez.service.PaymentService;
import com.perez.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {     //----users---------

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService userService;

    //----------------methods----------------------------------------------------------------------
    @PostMapping("/order")
    public ResponseEntity<PaymentResponse> createOrder(@RequestBody CreateOrderRequest req,
    @RequestHeader("Authorization") String jwt)
            throws Exception
    {

        User user = userService.findUserProfileByJwt(jwt);
        Order order = orderService.createOrder(req, user);
        System.out.println("req user " + user.getEmail());
        PaymentResponse res = paymentService.generatePaymentLink(order);

            return new ResponseEntity<>(res,HttpStatus.OK);



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
