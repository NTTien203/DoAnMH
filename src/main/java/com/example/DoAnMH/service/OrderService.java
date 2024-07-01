package com.example.DoAnMH.service;

import com.example.DoAnMH.model.*;
import com.example.DoAnMH.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private final CartRepository cartRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final CartItemRepository cartItemRepository;


    @Transactional
    public void createOrder(String address,String description) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User currentUser = userRepository.findByUsername(currentPrincipalName);
        String username=currentUser.getUsername();
        Order order = new Order();
        Date d1 = new Date();
        double sum=0;
        order.setUsername(username);
        order.setAddress(address);
        order.setDescription(description);
        order.setOrderDetails(new ArrayList<OrderDetail>());
        order.setOrderDate(d1);
        order = orderRepository.save(order);
        if (currentUser != null) {
            Cart cart = cartRepository.findByUserId(currentUser).orElseThrow(() -> new IllegalArgumentException("User not found: "));
            List<CartItem> cartItems=cart.getCartItems();
            for (int i=0;i<cartItems.size();i++) {

                OrderDetail detail = new OrderDetail();;
                detail.setOrder(order);
                detail.setQuantity(cartItems.get(i).getQuantity());
                detail.setProduct(cartItems.get(i).getProduct());
                detail.setSubtotal(cartItems.get(i).getProduct().getPrice()* cartItems.get(i).getQuantity());
                sum+=detail.getSubtotal();
                order.getOrderDetails().add(detail);
                CartItem cartItem=cart.getCartItems().get(i);
                orderDetailRepository.save(detail);
            }

            order.setTotalAmount(sum);
            orderRepository.save(order);

        }

    }
}
