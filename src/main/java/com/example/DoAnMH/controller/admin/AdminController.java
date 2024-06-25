package com.example.DoAnMH.controller.admin;

import com.example.DoAnMH.model.Category;
import com.example.DoAnMH.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    private final CategoryService categoryService;
    @RequestMapping("/admin")
    public String admin(){
        return "admin/home/index";
    }
//    @RequestMapping("/admin/category")
//    public String Categories(Model model){
//
//        List<Category> categories = categoryService.getAllCategory();
//        model.addAttribute("categories", categories);
//
//        return "admin/Category/index";
//    }
    @RequestMapping("/admin/user")
    public String User(){
        return "admin/User/index";
    }
//    @RequestMapping("/admin/products")
//    public String Product(){
//        return "admin/Products/index";
//    }
    @RequestMapping("/admin/order")
    public String Order(){
        return "admin/ShoppingOrder/index";
    }

}
