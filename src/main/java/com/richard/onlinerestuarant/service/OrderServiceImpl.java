package com.richard.onlinerestuarant.service;

import com.richard.onlinerestuarant.model.*;
import com.richard.onlinerestuarant.repository.*;
import com.richard.onlinerestuarant.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CartService cartService;
    @Override
    public Order createdorder(OrderRequest request, User user) throws Exception {
        Address shipAddress = request.getDeliveryAddress();
        Address saveAddress = addressRepository.save(shipAddress);

        if(!user.getAddresses().contains(saveAddress)){
            user.getAddresses().add(saveAddress);
            userRepository.save(user);
        }

        Restaurant restaurant=restaurantService.findRestaurantById(request.getRestaurantId());
        Order createdOrder=new Order();
        createdOrder.setCustomer(user);
        createdOrder.setCreatedAt(new Date());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.setDeliveryAddress(saveAddress);
        createdOrder.setRestaurant(restaurant);

        Cart cart=cartService.findCartByUserId(user.getId());

        List<OrderItem> orderItems=new ArrayList<>();

        for(CartItem cartItem:cart.getItem()){
            OrderItem orderItem=new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }


        return null;
    }

    @Override
    public Order updateOrder(Long orderId, String status) throws Exception {
        return null;
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {

    }

    @Override
    public List<Order> getUserOrder(Long userId) throws Exception {
        return null;
    }

    @Override
    public List<Restaurant> getRestaurantOrder(Long restaurantId, String orderStatus) throws Exception {
        return null;
    }
}
