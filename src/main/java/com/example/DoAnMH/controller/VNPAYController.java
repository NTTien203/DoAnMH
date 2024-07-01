package com.example.DoAnMH.controller;

import com.example.DoAnMH.model.*;
import com.example.DoAnMH.repository.*;
import com.example.DoAnMH.service.OrderService;
import com.example.DoAnMH.service.payment.VNPAYService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class VNPAYController {

    @Autowired
    private VNPAYService vnPayService;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final CartRepository cartRepository;
    @Autowired
    private final CartItemRepository cartItemRepository;


    @Autowired
    private final OrderDetailRepository orderDetailRepository;

    @GetMapping("/Vnpay")
    public String home(Model model) {
        long sum = 0;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User currentUser = userRepository.findByUsername(currentPrincipalName);
        if (currentUser != null) {
            Cart cart = cartRepository.findByUserId(currentUser).orElseThrow(() -> new IllegalArgumentException("User not found: "));
            for (CartItem cartItem : cart.getCartItems()) {
                double disc = cartItem.getProduct().getDiscountId().getDiscount();
                double sale = cartItem.getProduct().getPrice() * ((double) 1 -disc/(double) 100);
                sum+=cartItem.getQuantity()*sale;
            }
            if (cart != null) {
                model.addAttribute("sum", sum);
                model.addAttribute("UserName", currentUser.getUsername());
            }
            return "createOrder";
        }
        return "error";
    }


    @PostMapping("/submitOrder")
    public String submidOrder(@RequestParam("amount") int orderTotal,
                              @RequestParam("orderInfo") String orderInfo,
                              HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(request, orderTotal, orderInfo, baseUrl);
        return "redirect:" + vnpayUrl;
    }

    // Sau khi hoàn tất thanh toán, VNPAY sẽ chuyển hướng trình duyệt về URL này
    @GetMapping("/vnpay-payment-return")
    public String paymentCompleted(HttpServletRequest request, Model model) {
        int paymentStatus = vnPayService.orderReturn(request);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User currentUser = userRepository.findByUsername(currentPrincipalName);
        Cart cart = cartRepository.findByUserId(currentUser).orElseThrow(() -> new IllegalArgumentException("User not found: "));

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);
        List<CartItem> cartItems=cart.getCartItems();
        for (int i=0;i<cartItems.size();i++) {
            CartItem cartItem=cart.getCartItems().get(i);
            cartItemRepository.delete(cartItem);
        }

        return paymentStatus == 1 ? "ordersuccess" : "orderfail";
    }

}