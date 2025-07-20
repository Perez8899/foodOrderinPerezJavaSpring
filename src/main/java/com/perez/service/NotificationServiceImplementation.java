package com.perez.service;

import com.perez.model.Notification;
import com.perez.model.Order;
import com.perez.model.Restaurant;
import com.perez.model.User;
import com.perez.reporsitory.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotificationServiceImplementation implements NotificationService{
    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Notification sendOrderStatusNotification(Order order) {
        Notification notification = new Notification();
        notification.setMessage("Tu orden es "+order.getOrderStatus()+ " Orden ID es - "+order.getId());
        notification.setCustomer(order.getCustomer());
        notification.setSentAt(new Date());

        return notificationRepository.save(notification);
    }

    @Override
    public void sendRestaurantNotification(Restaurant restaurant, String message) {
        // TODO Auto-generated method stub

    }

    @Override
    public void sendPromotionalNotification(User user, String message) {
        // TODO Auto-generated method stu  b

    }

    @Override
    public List<Notification> findUsersNotification(Long userId) {
        // TODO Auto-generated method stub
        return notificationRepository.findByCustomerId(userId);
    }
}
