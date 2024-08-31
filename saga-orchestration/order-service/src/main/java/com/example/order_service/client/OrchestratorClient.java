package com.example.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "orchestrator-service", url = "http://localhost:9090")
public interface OrchestratorClient {

    @PostMapping("/orchestrator/start")
    void startSaga(@RequestParam("orderId") Long orderId);
}

