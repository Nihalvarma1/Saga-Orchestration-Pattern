package com.example.orchestration_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "order-service", url = "http://localhost:9091")
public interface OrderClient {

    @PutMapping("/orders/{orderId}/cancel")
    void cancelOrder(@RequestParam("orderId") Long orderId);

    @PutMapping("/orders/{orderId}/complete")
    void completeOrder(@RequestParam("orderId") Long orderId);
}
