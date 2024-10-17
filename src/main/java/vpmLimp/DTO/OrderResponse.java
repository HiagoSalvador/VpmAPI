package vpmLimp.DTO;

import vpmLimp.model.enums.OrderStatus;

import java.util.List;

public record OrderResponse(
        Long id,
        String status,
        String address,
        String city,
        List<ProductDetails> products,
        double totalAmount // Valor total do pedido
) {
    public record ProductDetails(
            Long id,
            String name,
            String description,
            double price,
            int quantity // Quantidade de cada produto
    ) {}
}