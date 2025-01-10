package com.perez.service;

import com.perez.Exception.CartException;
import com.perez.Exception.CartItemException;
import com.perez.Exception.FoodException;
import com.perez.Exception.UserException;
import com.perez.model.Cart;
import com.perez.model.CartItem;
import com.perez.request.AddCartItemRequest;

public interface CartService {  ////All Methods and Public

    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws UserException, FoodException, CartException, CartItemException;

    public CartItem updateCartItemQuantity(Long cartItemId,int quantity) throws CartItemException;

    public Cart removeItemFromCart(Long cartItemId, String jwt) throws UserException, CartException, CartItemException;

    public Long calculateCartTotals(Cart cart) throws UserException;

    public Cart findCartById(Long id) throws CartException;

    public Cart findCartByUserId(Long userId) throws CartException, UserException;

    public Cart clearCart(Long userId) throws CartException, UserException;
}
