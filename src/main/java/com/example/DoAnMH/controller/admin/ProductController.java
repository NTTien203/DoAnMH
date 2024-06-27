package com.example.DoAnMH.controller.admin;

import com.example.DoAnMH.model.Product;
import com.example.DoAnMH.service.CategoryService;
import com.example.DoAnMH.service.DiscountService;
import com.example.DoAnMH.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("admin/Products")
@RequiredArgsConstructor
public class ProductController {
    @Autowired
    private final ProductService productService;
    @Autowired
    private final CategoryService categoryService;
    @Autowired
    private final DiscountService discountService;
    @GetMapping
    public String listProduct(Model model){
        List<Product> products= productService.getAllProduct();
        model.addAttribute("products",products);
        return "Admin/Products/index";
    }

    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("product",new Product());
        model.addAttribute("categories",categoryService.getAllCategory());
        model.addAttribute("discounts",discountService.getAllDiscount());
        return "Admin/Products/add-Product";
    }
    @PostMapping("/add")
    public String addProduct(@Valid Product product,@NotNull BindingResult bindingResult, @RequestParam("files") MultipartFile[] files, RedirectAttributes redirectAttributes, Model model) throws IOException {
        model.addAttribute("product",product);
        model.addAttribute("categories",categoryService.getAllCategory());
        model.addAttribute("discounts",discountService.getAllDiscount());
        if(bindingResult.hasErrors()){
            var errors = bindingResult.getAllErrors() // Lấy tất cả lỗi
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new); // Chuyển các lỗi thành mảng String
            model.addAttribute("errors", errors);
            return "redirect:/admin/add-Product";
        }
        if(files == null){
            return "redirect:/admin/add-Product";
        }
        List<String>IM = new ArrayList<>();
        for(MultipartFile image : files){
            if(!image.isEmpty()){
                String imageUrl = saveImageStatic(image);
                IM.add("/images/"+imageUrl);
            }
        }
        product.setImages(IM);
        product.convertImagesToJson(IM);
         productService.addProduct(product);
        return "redirect:/admin/Products";
    }
    @GetMapping("/edit/{id}")
    public String ShowEditForm(@PathVariable Long id, Model model){
        Product product=productService.getProductById(id).orElseThrow(() -> new IllegalArgumentException("Invalid course id: " + id));
        model.addAttribute("product",product);
        model.addAttribute("categories",categoryService.getAllCategory());
        model.addAttribute("discounts",discountService.getAllDiscount());
        return "Admin/Products/UpdateProduct";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id,@Valid Product product, @NotNull BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, @RequestParam("files") MultipartFile[] files) throws IOException {
        model.addAttribute("product",product);
        model.addAttribute("categories",categoryService.getAllCategory());
        model.addAttribute("discounts",discountService.getAllDiscount());
        if (bindingResult.hasErrors()) {
            String[] errors = bindingResult.getAllErrors()
                    .stream()
                    .filter(error -> !(error instanceof FieldError) || !((FieldError) error).getField().equals("images"))
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            if (errors.length > 0) {
                model.addAttribute("errors", errors);
                return "Admin/Products/UpdateProduct";
            }
        }
        List<String>IM = new ArrayList<>();
        for(MultipartFile image : files){
            if(!image.isEmpty()){
                String imageUrl = saveImageStatic(image);
                IM.add("/images/"+imageUrl);
            }
        }
        Product c = productService.getProductById(id).orElseThrow();
        product.setImages(c.getImages());
        product.setImagesJson(c.getImagesJson());
        if(!IM.isEmpty()){
            product.setImages(IM);
            product.convertImagesToJson(IM);
        }
        productService.UpdateProduct(product);
        return "redirect:/admin/Products";
    }
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/Products";
    }
    private String saveImageStatic(MultipartFile image) throws IOException {
        File saveFile = new ClassPathResource("static/images").getFile();
        String fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(image.getOriginalFilename());
        Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + fileName);
        Files.copy(image.getInputStream(), path);
        return fileName;
    }
}
