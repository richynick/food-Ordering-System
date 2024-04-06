package com.richard.onlinerestuarant.repository;

import com.richard.onlinerestuarant.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {


}
