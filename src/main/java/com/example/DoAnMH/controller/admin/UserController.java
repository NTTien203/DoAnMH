package com.example.DoAnMH.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/User")
public class UserController {
    @GetMapping
    public String index(){
        return "Admin/User/index";
    }
}
