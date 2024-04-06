package com.richard.onlinerestuarant.service;

import com.richard.onlinerestuarant.model.Order;
import com.richard.onlinerestuarant.model.Restaurant;
import com.richard.onlinerestuarant.model.User;
import com.richard.onlinerestuarant.request.OrderRequest;

import java.util.List;

public interface OrderService {
    public Order createdorder(OrderRequest request, User user) throws Exception;
    public Order updateOrder(Long orderId, String status) throws Exception;
    public void cancelOrder(Long orderId) throws Exception;
    public List<Order> getUserOrder(Long userId) throws Exception;
    public List<Restaurant> getRestaurantOrder(Long restaurantId, String orderStatus) throws Exception;
}
