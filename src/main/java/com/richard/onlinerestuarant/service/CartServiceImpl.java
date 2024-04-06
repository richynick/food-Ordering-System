package com.richard.onlinerestuarant.service;

import com.richard.onlinerestuarant.model.Cart;
import com.richard.onlinerestuarant.model.CartItem;
import com.richard.onlinerestuarant.model.Food;
import com.richard.onlinerestuarant.model.User;
import com.richard.onlinerestuarant.repository.CartItemRepository;
import com.richard.onlinerestuarant.repository.CartRepository;
import com.richard.onlinerestuarant.request.AddCartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private FoodService foodService;
    @Autowired
    private CartItemRepository cartItemRepository;


    @Override
    public CartItem addItemToCart(AddCartItemRequest request, String jwt) throws Exception {
        User user =userService.findUserByJwtToken(jwt);
        Food food=foodService.findFoodById(request.getFoodId());
        Cart cart=cartRepository.findByCustomerId(user.getId());

        for (CartItem cartItem: cart.getItem()
             ) {
            if(cartItem.getFood().equals(food)){
                int newQuantity=cartItem.getQuantity()+request.getQuantity();
                return updateCartItemQuantity(cartItem.getId(),newQuantity);
            }
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setFood(food);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(request.getQuantity());
        newCartItem.setIngredients(request.getIngredients());
        newCartItem.setTotalPrice(request.getQuantity() * food.getPrice());

        CartItem savedCartItem= cartItemRepository.save(newCartItem);

        cart.getItem().add(savedCartItem);

        return savedCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
        if(cartItemOptional.isEmpty()){
            throw new Exception("Cart item is not found");
        }
        CartItem item = cartItemOptional.get();

        item.setQuantity(quantity);

        //5*100=5000 ( quantity * foodPrice = total)
        item.setTotalPrice(item.getFood().getPrice()*quantity);
        return cartItemRepository.save(item);
    }

    @Override
    public Cart removedItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user =userService.findUserByJwtToken(jwt);
        Cart cart=cartRepository.findByCustomerId(user.getId());
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);

        if(cartItemOptional.isEmpty()){
            throw new Exception("Cart item is not found");
        }
        CartItem item=cartItemOptional.get();
        cart.getItem().remove(item);
        return cartRepository.save(cart);
    }

    @Override
    public double calculateCartTotals(Cart cart) throws Exception {
        double total=0;
        for (CartItem cartItem: cart.getItem()
             ) {
            total+=cartItem.getFood().getPrice()*cartItem.getQuantity();
        }
        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> optionalCart = cartRepository.findById(id);
        if(optionalCart.isEmpty()){
            throw new Exception("Cart not found with id :" + id);
        }
        return optionalCart.get();
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
//        User user=userService.findUserByJwtToken(jwt);
        return cartRepository.findByCustomerId(userId);
    }

    @Override
    public Cart clearCart(Long userId) throws Exception {
//        User user=userService.findUserByJwtToken(jwt);
        Cart cart=findCartByUserId(userId);
        cart.getItem().clear();
        return cartRepository.save(cart);
    }
}
