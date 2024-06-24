package com.example.DoAnMH.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/Products")
public class ProductController {
    @GetMapping
    public String index(){
        return "Admin/Products/index";
    }
}
