package com.example.DoAnMH.controller.admin;

import com.example.DoAnMH.model.Category;
import com.example.DoAnMH.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("admin/Category")
@RequiredArgsConstructor
public class CategoryController {
    @Autowired
    private final CategoryService categoryService;

    @GetMapping()
    public String listCategory(Model model) {
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categories", categories);
        return "admin/Category/index";
    }
    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("category",new Category());
        return "admin/Category/add-Category";
    }
    @PostMapping("/add")
    public String addCategory(@Valid Category category, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin/Category";
        }
        categoryService.addCategory(category);
         return "redirect:/admin/Category";
    }

    @GetMapping("/edit/{id}")
    public String ShowEditForm(@PathVariable Long id,Model model){
        Category category=categoryService.getCategoryById(id).orElseThrow(() -> new IllegalArgumentException("Invalid course id: " + id));
        model.addAttribute("category",category);
        return "admin/Category/UpdateCategory";
    }

    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable Long id,@Valid Category category,BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "admin/Category/UpdateCategory";
        }
        categoryService.UpdateCategory(category);
        return "redirect:/admin/categories";
    }
}

