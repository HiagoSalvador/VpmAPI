package vpmLimp.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vpmLimp.DTO.CartRequest;


import vpmLimp.services.CartService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {

    @Autowired
    private CartService cartService;



    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addToCart(@RequestBody List<CartRequest> cartRequests) {
        Map<String, Object> result = cartService.addProductsToCart(cartRequests);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeFromCart(@RequestBody List<Long> productIdsToRemove) {
        cartService.removeProductsFromCart(productIdsToRemove);
        return ResponseEntity.noContent().build();
    }
}
