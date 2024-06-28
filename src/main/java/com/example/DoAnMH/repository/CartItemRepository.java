package com.example.DoAnMH.repository;

import com.example.DoAnMH.model.Cart;
import com.example.DoAnMH.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
