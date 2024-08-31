package com.example.orchestration_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "payment-service", url = "http://localhost:9093")
public interface PaymentClient {

    @PostMapping("/payments")
    boolean processPayment(@RequestParam Long orderId, @RequestParam double amount);

    @PutMapping("/payments/refund")
    boolean refundPayment(@RequestParam Long orderId);
}
