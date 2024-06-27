package com.example.DoAnMH.service;

import com.example.DoAnMH.model.CartItem;
import com.example.DoAnMH.model.Order;
import com.example.DoAnMH.model.OrderDetail;
import com.example.DoAnMH.repository.OrderDetailRepository;
import com.example.DoAnMH.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private CartService cartService; // Assuming you have a CartService

//    @Transactional
//    public Order createOrder(String customerName, List<CartItem> cartItems) {
//        Order order = new Order();
//        order.getUser(User);
//        order = orderRepository.save(order);
//        for (CartItem item : cartItems) {
//            OrderDetail detail = new OrderDetail();
//            detail.setOrder(order);
//            detail.setProduct(item.getProduct());
//            detail.setQuantity(item.getQuantity());
//            orderDetailRepository.save(detail);
//        }
//
//
//        cartService.clearCart();
//        return order;
//    }
}
