package com.perez.reporsitory;

import com.perez.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository <CartItem, Long> {
}
