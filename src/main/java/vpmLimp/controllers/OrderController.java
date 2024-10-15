package vpmLimp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import vpmLimp.DTO.OrderResponse;
import vpmLimp.DTO.OrderRequest;
import vpmLimp.model.UserModel;
import vpmLimp.services.OrderService;

import java.util.List;


@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<List<String>> createOrder(@RequestBody OrderRequest orderRequest) {
        List<String> productNames = orderService.createOrder(orderRequest.productIds());
        return ResponseEntity.ok(productNames);
    }

    @GetMapping("/user")
    public ResponseEntity<List<OrderResponse>> getUserOrders(@AuthenticationPrincipal UserModel user) {
        List<OrderResponse> orderResponses = orderService.getUserOrders(user.getId());
        return ResponseEntity.ok(orderResponses);
    }
}
