package com.example.DoAnMH.repository;

import com.example.DoAnMH.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p ORDER BY p.discountId.discount DESC")
    List<Product> findAllByOrderByDiscountDesc();
    @Query("SELECT p FROM Product p WHERE p.categoryId.name = :categoryName")
    List<Product> findAllByCategoryName(@Param("categoryName") String categoryName);

}
