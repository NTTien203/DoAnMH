package com.example.DoAnMH.controller.user;

import com.example.DoAnMH.service.CartService;
import com.example.DoAnMH.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class OrderUserController {
    @Autowired
    private final OrderService orderService;
    @Autowired
    private final CartService cartService;
    @GetMapping("Cart/checkout")
    public String checkout() {
        return "/User/checkout";
    }
    @PostMapping("/submit")
    public String submitOrder(String address,String description) {

        orderService.createOrder(address,description);
        return "/User/order-confirmation";
    }

}