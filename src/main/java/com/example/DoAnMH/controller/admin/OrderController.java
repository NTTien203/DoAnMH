package com.example.DoAnMH.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/ShoppingOrder")
public class OrderController {
    @GetMapping
    public String index(){
        return "Admin/ShoppingOrder/index";
    }
}
