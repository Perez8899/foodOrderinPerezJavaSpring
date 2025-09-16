package com.perez.reporsitory;

import com.perez.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.customer.id = :userId")
    List<Order> findAllUserOrders(@Param("userId")Long userId);

    @Query("SELECT o FROM Order o WHERE o.restaurant.id = :restaurantId")
    List<Order> findOrdersByRestaurantId(@Param("restaurantId") Long restaurantId);


    @Query("SELECT o FROM Order o WHERE o.restaurant.id = :restaurantId AND o.createdAt BETWEEN :startDate AND :endDate")
    List<Order> findOrdersByRestaurantAndDateRange(
            @Param("restaurantId") Long restaurantId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );

}
