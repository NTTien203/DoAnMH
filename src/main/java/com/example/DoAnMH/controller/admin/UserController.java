package com.example.DoAnMH.controller.admin;

import com.example.DoAnMH.model.User;
import com.example.DoAnMH.repository.UserRepository;
import com.example.DoAnMH.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin/User")
public class UserController {
    private final UserRepository userRepository;
    @GetMapping
    public String index(Model model){
        List<User> users=userRepository.findAll();
        model.addAttribute("users", users);
        return "Admin/User/index";
    }
}
