package vpmLimp.DTO;


import java.util.List;

public record OrderResponse(
        Long id,
        String status,
        String address,
        String city,
        List<ProductDetails> products,
        double totalAmount
) {
    public record ProductDetails(
            Long id,
            String name,
            String description,
            double price,
            int quantity
    ) {
    }
}