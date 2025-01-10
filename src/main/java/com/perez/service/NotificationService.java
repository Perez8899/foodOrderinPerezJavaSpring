package com.perez.service;

import com.perez.model.Notification;
import com.perez.model.Order;
import com.perez.model.Restaurant;
import com.perez.model.User;

import java.util.List;

public interface NotificationService {

    public Notification sendOrderStatusNotification(Order order);
    public void sendRestaurantNotification(Restaurant restaurant, String message);
    public void sendPromotionalNotification(User user, String message);

    public List<Notification> findUsersNotification(Long userId);
}
