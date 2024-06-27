package com.example.DoAnMH.repository;

import com.example.DoAnMH.model.Cart;
import com.example.DoAnMH.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
