package com.example.order_service.controller;

import com.example.order_service.client.OrchestratorClient;
import com.example.order_service.model.Orders;
import com.example.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrchestratorClient orchestratorClient;

    @PostMapping
    public Orders createOrder() {
        Orders order = new Orders();
        order.setStatus("Pending");
        order = orderRepository.save(order);
        // Send an event to the orchestrator
        orchestratorClient.startSaga(order.getId());
        return order;
    }

    @PutMapping("/{orderId}/cancel")
    public Orders cancelOrder(@PathVariable Long orderId) {
        Optional<Orders> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Orders order = orderOptional.get();
            order.setStatus("Canceled");
            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Order not found");
        }
    }

    @PutMapping("/{orderId}/complete")
    public Orders completeOrder(@PathVariable Long orderId) {
        Optional<Orders> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Orders order = orderOptional.get();
            order.setStatus("Completed");
            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Order not found");
        }
    }
}
