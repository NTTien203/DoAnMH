package com.example.DoAnMH.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("adminCategoryController")
@RequestMapping("admin/Category")
public class CategoryController {
    @GetMapping
    public String index(){
        return "Admin/Category/index";
    }
}
