package com.example.DoAnMH.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
   @NotNull(message = "Order date is mandatory")
   private Date orderDate;
    @NotNull(message = "Total amount is mandatory")
    private double totalAmount;
    @NotNull
    private String address;

    private String description;
    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;
}

