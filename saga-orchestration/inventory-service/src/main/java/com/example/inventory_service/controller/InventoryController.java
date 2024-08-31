package com.example.inventory_service.controller;

import com.example.inventory_service.model.Inventory;
import com.example.inventory_service.repo.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryRepository inventoryRepository;

    @PostMapping("/reserve")
    public boolean reserveInventory(@RequestParam Long orderId, @RequestParam String itemName, @RequestParam int quantity) {
        Optional<Inventory> inventoryOptional = inventoryRepository.findByItemName(itemName);
        if (inventoryOptional.isPresent()) {
            Inventory inventory = inventoryOptional.get();
            if (inventory.getQuantity() >= quantity) {
                inventory.setQuantity(inventory.getQuantity() - quantity);
                inventoryRepository.save(inventory);
                System.out.println("Inventory reserved for Order ID: " + orderId);
                return true;
            } else {
                System.out.println("Not enough inventory for Order ID: " + orderId);
                return false;
            }
        } else {
            System.out.println("Inventory item not found for Order ID: " + orderId);
            return false;
        }
    }

    @PutMapping("/release")
    public boolean releaseInventory(@RequestParam Long orderId, @RequestParam String itemName, @RequestParam int quantity) {
        Optional<Inventory> inventoryOptional = inventoryRepository.findByItemName(itemName);
        if (inventoryOptional.isPresent()) {
            Inventory inventory = inventoryOptional.get();
            inventory.setQuantity(inventory.getQuantity() + quantity);
            inventoryRepository.save(inventory);
            System.out.println("Inventory released for Order ID: " + orderId);
            return true;
        } else {
            System.out.println("Inventory item not found for Order ID: " + orderId);
            return false;
        }
    }
}

