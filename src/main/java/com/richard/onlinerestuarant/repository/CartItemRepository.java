package com.richard.onlinerestuarant.repository;

import com.richard.onlinerestuarant.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
