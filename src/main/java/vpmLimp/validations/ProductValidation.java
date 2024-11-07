package vpmLimp.validations;

import org.springframework.stereotype.Component;
import vpmLimp.DTO.ProductRequest;

@Component
public class ProductValidation {


    public void validateProductRequest(ProductRequest productRequest) {
        if (productRequest.name() == null || productRequest.name().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty.");
        }

        if (productRequest.description() == null || productRequest.description().trim().isEmpty()) {
            throw new IllegalArgumentException("Product description cannot be null or empty.");
        }

        if (productRequest.price() == null || productRequest.price() < 0) {
            throw new IllegalArgumentException("Product price must be greater than or equal to zero.");
        }

        if (productRequest.quantity() == null || productRequest.quantity() < 0) {
            throw new IllegalArgumentException("Product quantity must be greater than or equal to zero.");
        }

        if (productRequest.imageUrl() == null || productRequest.imageUrl().trim().isEmpty()) {
            throw new IllegalArgumentException("Product image URL cannot be null or empty.");
        }
    }

    public void validateProductUpdateQuantity(Integer quantity) {
        if (quantity == null || quantity < 0) {
            throw new IllegalArgumentException("Product quantity must be greater than or equal to zero.");
        }
    }
}
