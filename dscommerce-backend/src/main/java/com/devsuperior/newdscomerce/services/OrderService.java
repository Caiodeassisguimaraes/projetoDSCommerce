package com.devsuperior.newdscomerce.services;

import com.devsuperior.newdscomerce.dto.OrderDto;
import com.devsuperior.newdscomerce.dto.OrderItemDto;
import com.devsuperior.newdscomerce.entities.*;
import com.devsuperior.newdscomerce.repositories.OrderItemRepository;
import com.devsuperior.newdscomerce.repositories.OrderRepository;
import com.devsuperior.newdscomerce.repositories.ProductRepository;
import com.devsuperior.newdscomerce.services.exceptions.ResourseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repositoty;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Transactional(readOnly = true)
    public OrderDto findById(Long id){
        Order order = repositoty.findById(id).orElseThrow(() -> new ResourseNotFoundException("Recurso não encontrado"));
        authService.validateSelfOrAdmin(order.getClient().getId());
        return new OrderDto(order);

    }
    @Transactional
    public OrderDto insert(OrderDto dto) {
        Order order = new Order();
        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WHAITING_PAYMENT);
        User user = userService.authenticated();
        order.setClient(user);
        for (OrderItemDto orderItemDto : dto.getItems()){
            Product product = productRepository.getReferenceById(orderItemDto.getProductId());
            OrderItem item = new OrderItem(order, product, orderItemDto.getQuantity(), product.getPrice());
            order.getItems().add(item);
        }
        repositoty.save(order);
        orderItemRepository.saveAll(order.getItems());

        return new OrderDto(order);
    }
}
