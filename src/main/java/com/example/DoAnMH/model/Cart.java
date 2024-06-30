package com.example.DoAnMH.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "Cart_Id")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long CartId;
    @OneToOne
    @JoinColumn(name = "id")
    User userId;

    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY)
    private List<CartItem> CartItems;

}