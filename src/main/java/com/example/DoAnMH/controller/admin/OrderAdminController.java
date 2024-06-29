package com.example.DoAnMH.controller.admin;

import com.example.DoAnMH.model.Order;
import com.example.DoAnMH.model.User;
import com.example.DoAnMH.repository.OrderRepository;
import com.example.DoAnMH.repository.UserRepository;
import com.example.DoAnMH.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Controller
@RequiredArgsConstructor
@RequestMapping("admin/Order")
public class OrderAdminController {
    private final OrderRepository orderRepository;
    @GetMapping
    public String index(Model model){
        List<Order> orders=orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "Admin/ShoppingOrder/index";
    }
}
