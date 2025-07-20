package com.perez.service;

import com.perez.Exception.CartException;
import com.perez.Exception.OrderException;
import com.perez.Exception.RestaurantException;
import com.perez.Exception.UserException;
import com.perez.model.Order;
import com.perez.model.User;
import com.perez.request.CreateOrderRequest;

import java.util.List;

public interface OrderService {  //All Methods and Public

    //public nousado createOrder(CreateOrderRequest order, User user) throws UserException, RestaurantException, CartException, StripeException; //ORIGINAL
    public Order createOrder(CreateOrderRequest order, User user) throws UserException, RestaurantException, CartException;

    public Order updateOrder(Long orderId, String orderStatus) throws OrderException;

    public void cancelOrder(Long orderId) throws OrderException;

    public List<Order> getUserOrders(Long userId) throws OrderException;

    public List<Order> getOrdersOfRestaurant(Long restaurantId,String orderStatus) throws OrderException, RestaurantException;

    public Order findOrderById (Long orderId) throws Exception;
}
