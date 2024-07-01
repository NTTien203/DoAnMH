package com.example.DoAnMH.controller.admin;


import com.example.DoAnMH.model.Category;
import com.example.DoAnMH.model.Discount;
import com.example.DoAnMH.repository.DiscountRepository;
import com.example.DoAnMH.service.CategoryService;
import com.example.DoAnMH.service.DiscountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("admin/Discount")
@RequiredArgsConstructor
public class DiscountController {
    @Autowired
    private final DiscountService discountService;

    @GetMapping()
    public String listDiscount(Model model) {
        List<Discount> discounts = discountService.getAllDiscount();
        model.addAttribute("discounts", discounts);
        return "admin/Discount/index";
    }
    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("discount",new Discount());
        return "admin/Discount/add-Discount";
    }
    @PostMapping("/add")
    public String addCategory(@Valid Discount discount, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin/Discount";
        }
        discountService.addCategory(discount);
        return "redirect:/admin/Discount";
    }

    @GetMapping("/edit/{id}")
    public String ShowEditForm(@PathVariable Long id, Model model){
        Discount discount=discountService.getCategoryById(id).orElseThrow(() -> new IllegalArgumentException("Invalid course id: " + id));
        model.addAttribute("discount",discount);
        return "admin/Discount/UpdateDiscount";
    }

    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable Long id,@Valid Discount discount,BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "admin/Discount/UpdateDiscount";
        }
        discountService.UpdateDiscount(discount);
        return "redirect:/admin/Discount";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        discountService.deleteDiscount(id);
        return "redirect:/admin/Discount";
    }

}
