package com.rovelazquez.ecommerce_jwt.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rovelazquez.ecommerce_jwt.entity.CartItem;
import com.rovelazquez.ecommerce_jwt.entity.Order;
import com.rovelazquez.ecommerce_jwt.entity.OrderDetail;
import com.rovelazquez.ecommerce_jwt.entity.OrderStatus;
import com.rovelazquez.ecommerce_jwt.entity.userApp;
import com.rovelazquez.ecommerce_jwt.repository.CartItemRepository;
import com.rovelazquez.ecommerce_jwt.repository.OrderRepository;
import com.rovelazquez.ecommerce_jwt.repository.UserRepository;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final CartService cartService;
    private final UserRepository userRepository; 

    @Transactional
    public Order placeOrder(userApp user, String address, String city, 
            String postcode, String mobile, String paymentMethod) {
        List<CartItem> cartItems = cartItemRepository.findAllByUser(user);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("No se puede realizar el pedido: el carrito está vacío");
        }
        Double total = cartService.calculateTotalPrice(cartItems) + 3.00;

        Order order = Order.builder()
                .user(user)
                .date(LocalDateTime.now())
                .total(total)
                .status(OrderStatus.PENDING)
                .address(address)
                .city(city)
                .postcode(postcode)
                .mobile(mobile)
                .paymentMethod(paymentMethod)
                .build();

        boolean userChanged = false;
        if (user.getAddress() == null || user.getAddress().isEmpty()) {
            user.setAddress(address);
            userChanged = true;
        }
        if (user.getCity() == null || user.getCity().isEmpty()) {
            user.setCity(city);
            userChanged = true;
        }
        if (user.getPostcode() == null || user.getPostcode().isEmpty()) {
            user.setPostcode(postcode);
            userChanged = true;
        }
        if (user.getMovile() == null || user.getMovile().isEmpty()) {
            user.setMovile(mobile);
            userChanged = true;
        }
        if (userChanged) {
            userRepository.save(user); 
        }
       
        List<OrderDetail> details = new ArrayList<>();
        
        for (CartItem item : cartItems) {
            OrderDetail detail = OrderDetail.builder()
                    .order(order) 
                    .product(item.getProduct())
                    .quantity(item.getQuantity())
                    .price(item.getProduct().getPrice())
                    .build();
            details.add(detail);
        }
        order.setDetails(details);
        Order savedOrder = orderRepository.save(order);
        cartItemRepository.deleteAll(cartItems);
        return savedOrder;
    }
}