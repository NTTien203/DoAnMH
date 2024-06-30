package com.example.DoAnMH.controller.user;

import com.example.DoAnMH.model.*;
import com.example.DoAnMH.repository.CartItemRepository;
import com.example.DoAnMH.repository.CartRepository;
import com.example.DoAnMH.repository.UserRepository;
import com.example.DoAnMH.service.CartService;
import com.example.DoAnMH.service.CategoryService;
import com.example.DoAnMH.service.ProductService;
import jakarta.persistence.GeneratedValue;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("cart")
@RequiredArgsConstructor
public class CartController {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final CartRepository cartRepository;

    @Autowired
    private  final ProductService productService;
    @Autowired
    private final CartItemRepository cartItemRepository;



    @GetMapping
    public String showCart( User user, Model model) {
        int countCard=0;
        double sum=0;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User currentUser = userRepository.findByUsername(currentPrincipalName);

        if(currentUser!=null){
            Cart cart = cartRepository.findByUserId(currentUser).orElseThrow(() -> new IllegalArgumentException("User not found: " ));
            for (CartItem cartItem: cart.getCartItems()) {
                double disc = cartItem.getProduct().getDiscountId().getDiscount();
                double sale = cartItem.getProduct().getPrice() * ((double) 1 -disc/(double) 100);
                sum+=cartItem.getQuantity()*sale;
                countCard+=  cartItem.getQuantity();
                cartItem.getProduct().setImages(cartItem.getProduct().convertJsonToImages());
                cartItem.getProduct().setPriceDiscount(sale);
            }
            if(cart!=null){
                model.addAttribute("cartItems", cart.getCartItems());
                model.addAttribute("user",user);
                model.addAttribute("countCard",countCard);
                model.addAttribute("sum",sum);
                model.addAttribute("productCount",cart.getCartItems().size());
                return "/User/AddtoCart";

            }
        }
        return "error";
    }

    @PostMapping("/add")
    @Transactional
    public String addToCart(@RequestParam("itemId") Long id,
                            @RequestParam("quantity") int quantity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User currentUser = userRepository.findByUsername(currentPrincipalName);
        Product product= productService.getProductById(id).orElseThrow(() -> new IllegalArgumentException("User not find: " ));
        if (currentUser != null) {
            Cart cart = cartRepository.findByUserId(currentUser).orElse(null);
            if (cart == null) {
                List<CartItem> cartItems=new ArrayList<CartItem>() ;
                cart = new Cart();
                cart.setUserId(currentUser);
                cart.setCartItems(cartItems);
                cartRepository.save(cart);
            }

            CartItem existingItem = cart.getCartItems().stream()
                    .filter(item -> item.getProduct().getId().equals(id))
                    .findFirst()
                    .orElse(null);
            if (existingItem != null) {

                existingItem.setQuantity(existingItem.getQuantity() + quantity);
            } else {
                CartItem newItem = new CartItem();
                newItem.setProduct(product);
                newItem.setQuantity(quantity);
                newItem.setCart(cart);
                cartItemRepository.save(newItem);
                cart.getCartItems().add(newItem);
            }

            cartRepository.save(cart);

            return "redirect:/cart";
        }

        return "error";
    }

    @GetMapping("/delete/{id}")
    public String deleteCart(@PathVariable Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User currentUser = userRepository.findByUsername(currentPrincipalName);

        Optional<Cart> optionalCart = cartRepository.findByUserId(currentUser);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            List<CartItem> cartItems = cart.getCartItems();

            CartItem itemToRemove = cartItems.stream()
                    .filter(item -> item.getProduct().getId().equals(id))
                    .findFirst()
                    .orElse(null);

            if (itemToRemove != null) {
                cartItems.remove(itemToRemove);
                cartItemRepository.delete(itemToRemove);
                cartRepository.save(cart);
            }
        }

        return "redirect:/cart";
    }


}