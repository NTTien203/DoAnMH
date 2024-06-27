package com.example.DoAnMH.controller.User;

import com.example.DoAnMH.model.Cart;
import com.example.DoAnMH.model.Category;
import com.example.DoAnMH.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("User/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    //@GetMapping("User/cart/{id}")
    @GetMapping("/show/{id}")
    public String showCart(Model model, @PathVariable Long id) {
        int sum=0;

        Cart cart=cartService.getCartById(id).orElseThrow(() -> new IllegalArgumentException("Invalid course id: " + id));
        for(int i=0;i<cartService.getCartItem(cart.getCartId()).size();i++){
            sum=sum+=cartService.getCartItem(cart.getCartId()).get(i).getProduct().getPrice();
        }
        model.addAttribute("cartItems", cartService.getCartItem(cart.getCartId()));
        model.addAttribute("cart",cart);
        model.addAttribute("countCard",cartService.getCartItem(cart.getCartId()).size());
        model.addAttribute("sum",sum);
        return "/User/AddtoCart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity,@RequestParam Long CartId) {
        cartService.addToCart(productId,quantity,CartId);
        return "redirect:/User/cart";
    }
    

}
