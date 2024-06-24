package com.example.DoAnMH.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String index() {
        return "User/HomePage"; // This should match the name of your HTML file without the .html extension
    }


    @GetMapping("/Detail")
    public String Detailpage() {
        return "User/DetailPage"; // This should match the name of your HTML file without the .html extension
    }

    @GetMapping("/Product")
    public String Productpage() {
        return "User/ProductPage"; // This should match the name of your HTML file without the .html extension
    }

    @GetMapping("/login")
    public String login() {
        return "User/Login"; // This should match the name of your HTML file without the .html extension
    }
    @GetMapping("/register")
    public String DangKy() {
        return "User/Rigister"; // This should match the name of your HTML file without the .html extension
    }

    @GetMapping("/AddtoCart")
    public String AddtoCart() {
        return "User/AddtoCart"; // This should match the name of your HTML file without the .html extension
    }

}
