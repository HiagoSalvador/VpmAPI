package vpmLimp.DTO;



public record UpdateProduct(
        String name,
        String description,
        Double price,
        int quantity,
        String imageUrl
) {}

