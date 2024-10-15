package vpmLimp.DTO;

import vpmLimp.model.enums.OrderStatus;

import java.util.List;

public record OrderResponse(
        Long orderId,
        OrderStatus status,
        String address,
        String city,
        List<ProductDetails> products
) {

    public record ProductDetails(Long id, String name, String description, Double price) {
    }
}
