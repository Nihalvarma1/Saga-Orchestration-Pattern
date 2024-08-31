package com.example.orchestration_service.controller;

import com.example.orchestration_service.client.InventoryClient;
import com.example.orchestration_service.client.OrderClient;
import com.example.orchestration_service.client.PaymentClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orchestrator")
public class OrchestratorController {

    @Autowired
    private InventoryClient inventoryClient;

    @Autowired
    private PaymentClient paymentClient;

    @Autowired
    private OrderClient orderClient;

    @PostMapping("/start")
    public void startSaga(@RequestParam Long orderId) {
        // Assume itemName and quantity are known (could come from Order details)
        String itemName = "Item A";
        int quantity = 1;
        double paymentAmount = 100.0;

        // Step 1: Reserve inventory
        boolean inventoryReserved = inventoryClient.reserveInventory(orderId, itemName, quantity);
        if (!inventoryReserved) {
            orderClient.cancelOrder(orderId);
            return;
        }

        // Step 2: Process payment
        boolean paymentProcessed = paymentClient.processPayment(orderId, paymentAmount);
        if (!paymentProcessed) {
            inventoryClient.releaseInventory(orderId, itemName, quantity);
            orderClient.cancelOrder(orderId);
            return;
        }

        // Step 3: Complete order
        orderClient.completeOrder(orderId);
    }
}

