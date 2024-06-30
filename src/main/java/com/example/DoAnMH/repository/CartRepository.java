package com.example.DoAnMH.repository;

import com.example.DoAnMH.model.Cart;
import com.example.DoAnMH.model.Category;
import com.example.DoAnMH.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(User user);
}