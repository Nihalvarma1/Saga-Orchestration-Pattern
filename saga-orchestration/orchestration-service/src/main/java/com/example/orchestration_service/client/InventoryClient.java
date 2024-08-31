package com.example.orchestration_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "inventory-service", url = "http://localhost:9092")
public interface InventoryClient {

    @PostMapping("/inventory/reserve")
    boolean reserveInventory(@RequestParam Long orderId, @RequestParam String itemName, @RequestParam int quantity);

    @PutMapping("/inventory/release")
    boolean releaseInventory(@RequestParam Long orderId, @RequestParam String itemName, @RequestParam int quantity);
}
