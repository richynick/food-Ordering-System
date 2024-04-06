package com.richard.onlinerestuarant.service;

import com.richard.onlinerestuarant.model.Cart;
import com.richard.onlinerestuarant.model.CartItem;
import com.richard.onlinerestuarant.model.User;
import com.richard.onlinerestuarant.request.AddCartItemRequest;

public interface CartService {
    public CartItem addItemToCart(AddCartItemRequest request, String jwt) throws Exception;

    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception;

    public Cart removedItemFromCart(Long cartItemId, String jwt) throws Exception;

    public double calculateCartTotals(Cart cart) throws Exception;

    public Cart findCartById(Long id) throws Exception;

    public Cart findCartByUserId(Long userId) throws Exception;

    public Cart clearCart(Long userId) throws Exception;

}
