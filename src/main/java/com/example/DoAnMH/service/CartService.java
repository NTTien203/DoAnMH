package com.example.DoAnMH.service;
import com.example.DoAnMH.model.Cart;
import com.example.DoAnMH.model.CartItem;
import com.example.DoAnMH.model.Category;
import com.example.DoAnMH.model.Product;
import com.example.DoAnMH.repository.CartRepository;
import com.example.DoAnMH.repository.ProductRepository;
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

    public List<CartItem> getCartItem(Long CardId){
        return cartRepository.findById(CardId).get().getCartItems();
    }
    public Optional<Cart> getCartById(Long id) {
        return cartRepository.findById(id);
    }
    public void addToCart(Long productId, int quantity,Long CartId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
        CartItem cartItem= new CartItem(product,quantity);
        Cart cart=cartRepository.findById(CartId).orElseThrow(()->new IllegalArgumentException("Product not found: " + CartId));
        cart.getCartItems().add(cartItem);
    }
    public void clearCart() {

    }


}