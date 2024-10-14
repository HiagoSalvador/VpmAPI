package vpmLimp.DTO;

import lombok.*;
import vpmLimp.model.ProductModel;

public record ProductResponse(Long id, String name, String description, Double price) {

    public ProductResponse(ProductModel product) {
        this(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }
}
