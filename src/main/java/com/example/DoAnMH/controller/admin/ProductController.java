package com.example.DoAnMH.controller.admin;

import com.example.DoAnMH.model.Category;
import com.example.DoAnMH.model.Product;
import com.example.DoAnMH.service.CategoryService;
import com.example.DoAnMH.service.ProductService;
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
@RequestMapping("admin/Products")
@RequiredArgsConstructor
public class ProductController {
    @Autowired
    private final ProductService productService;
    @Autowired
    private final CategoryService categoryService;
    @GetMapping
    public String listProduct(Model model){
        List<Product> products= productService.getAllProduct();
        model.addAttribute("products",products);
        return "admin/Products/index";
    }

    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("product",new Product());
        model.addAttribute("categories",categoryService.getAllCategory());
        return "admin/Products/add-Product";
    }
    @PostMapping("/add")
    public String addProduct(@Valid Product product, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "redirect:/admin/Products";
        }
        productService.addProduct(product);
        return "redirect:/admin/Products";
    }
    @GetMapping("/edit/{id}")
    public String ShowEditForm(@PathVariable Long id, Model model){
        Product product=productService.getProductById(id).orElseThrow(() -> new IllegalArgumentException("Invalid course id: " + id));
        model.addAttribute("product",product);
        model.addAttribute("categories",categoryService.getAllCategory());
        return "admin/Products/UpdateProduct";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id,@Valid Product product,BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "admin/Category/UpdateCategory";
        }
        productService.UpdateProduct(product);
        return "redirect:/admin/Products";
    }
}
