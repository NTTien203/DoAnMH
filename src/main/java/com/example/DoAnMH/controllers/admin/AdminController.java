package com.example.DoAnMH.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
    @RequestMapping("/admin")
    public String admin(){
        return "admin/home/index";
    }
    @RequestMapping("/admin/category")
    public String Categories(){
        return "admin/Category/index";
    }
    @RequestMapping("/admin/user")
    public String User(){
        return "admin/User/index";
    }
    @RequestMapping("/admin/products")
    public String Product(){
        return "admin/Products/index";
    }
    @RequestMapping("/admin/order")
    public String Order(){
        return "admin/ShoppingOrder/index";
    }

}
