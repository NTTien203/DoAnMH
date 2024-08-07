package com.example.DoAnMH.service;
import com.example.DoAnMH.model.*;
import com.example.DoAnMH.repository.CartRepository;
import com.example.DoAnMH.repository.ProductRepository;
import com.example.DoAnMH.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@SessionScope
public class CartService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;

    public List<CartItem> getCartItem(Long UserId){
        List<CartItem> cartItems = cartRepository.findById(UserId).get().getCartItems();
        return cartItems;
    }
    public Cart getCartByUser(User user) {
        Optional<Cart> optionalCart = cartRepository.findByUserId(user);
        return optionalCart.orElseGet(() -> createCartForUser(user));
    }
    public Optional<Cart> getCartById(Long id) {
        return cartRepository.findById(id);
    }
    private Cart createCartForUser(User user) {
        Cart cart = new Cart();
        cart.setUserId(user);
        return cartRepository.save(cart);
    }




}