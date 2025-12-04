package com.rovelazquez.ecommerce_jwt.controller;


import com.rovelazquez.ecommerce_jwt.entity.CartItem;
import com.rovelazquez.ecommerce_jwt.entity.userApp;
import com.rovelazquez.ecommerce_jwt.service.CartService;
import com.rovelazquez.ecommerce_jwt.service.OrderService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final CartService cartService;
    private final OrderService orderService; 

   
    @GetMapping("/carrito")
    public String viewCart(Model model, @AuthenticationPrincipal userApp user) {
        
        if (user == null) {
            return "redirect:/login";
        }

        
        List<CartItem> cartItems = cartService.getCartItems(user);
        Double totalPrice = cartService.calculateTotalPrice(cartItems);
        
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
      
        return "order/cart";
    }

   
    @GetMapping("/checkout")
    public String viewCheckout(Model model, @AuthenticationPrincipal userApp user) {

        if (user == null) {
            return "redirect:/login";
        }

        List<CartItem> cartItems = cartService.getCartItems(user);
        
        Double totalPrice = cartService.calculateTotalPrice(cartItems);

        model.addAttribute("cartItems", cartItems); 
        model.addAttribute("totalPrice", totalPrice); 
        model.addAttribute("user", user);
       return "order/checkout";
    }
    
    @PostMapping("/checkout")
    public String processCheckout(
            @AuthenticationPrincipal userApp user,
            @RequestParam("address") String address,
            @RequestParam("city") String city,
            @RequestParam("postcode") String postcode,
            @RequestParam("mobile") String mobile,
            @RequestParam("paymentMethod") String paymentMethod,
            RedirectAttributes redirectAttributes
    ) {
        
        if (user == null) {
            return "redirect:/login";
        }

        try {
            orderService.placeOrder(user, address, city, postcode, mobile, paymentMethod);
            return "redirect:/home?orderSuccess=true"; 

        } catch (RuntimeException e) {
             if (e.getMessage().contains("el carrito está vacío")) {
                return "redirect:/checkout?emptyCart=true";
            }
            
            
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/checkout";
        }
    }
}
