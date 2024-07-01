package com.example.DoAnMH.controller;

import com.example.DoAnMH.model.User;
import com.example.DoAnMH.service.CategoryService;
import com.example.DoAnMH.service.ProductService;
import com.example.DoAnMH.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class HomeController {
    @Autowired
    private  final UserService userService;
//    @GetMapping("/")
//    public String index() {
//        return "User/HomePage"; // This should match the name of your HTML file without the .html extension
//    }


//    @GetMapping("/Detail")
//    public String Detailpage() {
//        return "User/DetailPage"; // This should match the name of your HTML file without the .html extension
//    }

//    @GetMapping("/Product")
//    public String Productpage() {
//        return "User/ProductPage"; // This should match the name of your HTML file without the .html extension
//    }

    @GetMapping("/login")
    public String login() {
        return "User/Login"; // This should match the name of your HTML file without the .html extension
    }
    @GetMapping("/register")
    public String DangKy(Model model) {
        model.addAttribute("user",new User());
        return "User/Rigister"; // This should match the name of your HTML file without the .html extension
    }
    @PostMapping("/register")
    public String DK(@Valid User u , @NotNull BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            if (bindingResult.hasErrors()) { // Kiểm tra nếu có lỗi validate
                var errors = bindingResult.getAllErrors() // Lấy tất cả lỗi
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toArray(String[]::new); // Chuyển các lỗi thành mảng String
                model.addAttribute("errors", errors);
                return "User/Rigister";
            }
        }
        if(!u.getPassword().equals(u.getConfirmpassword())) {
            model.addAttribute("errorMessage", "Mật khẩu không khớp với xác nhận!!");
            model.addAttribute("user",u);
            return "User/Rigister";
        }
        userService.save(u);
        userService.setDefaultRole(u.getUsername());
        return "User/Login";
    }
    @GetMapping("/AddtoCart")
    public String AddtoCart() {
        return "User/AddtoCart"; // This should match the name of your HTML file without the .html extension
    }

}