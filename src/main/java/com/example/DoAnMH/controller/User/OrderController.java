package com.example.DoAnMH.controller.User;

import com.example.DoAnMH.service.CartService;
import com.example.DoAnMH.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;
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

