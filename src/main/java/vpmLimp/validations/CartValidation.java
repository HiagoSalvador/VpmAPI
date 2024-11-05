package vpmLimp.validations;

import org.springframework.stereotype.Component;
import vpmLimp.DTO.CartRequest;
import vpmLimp.model.ProductModel;
import vpmLimp.repositories.ProductModelRepository;

import java.util.List;

@Component
public class CartValidation {



    private final ProductModelRepository productModelRepository;

    public CartValidation(ProductModelRepository productModelRepository) {
        this.productModelRepository = productModelRepository;
    }

    public void validateCartRequests(List<CartRequest> cartRequests) {
        if (cartRequests == null || cartRequests.isEmpty()) {
            throw new IllegalArgumentException("The list of products for the cart cannot be empty.");
        }

        for (CartRequest cartRequest : cartRequests) {
            if (cartRequest.productId() == null) {
                throw new IllegalArgumentException("Product ID must be provided.");
            }
            if (cartRequest.quantity() <= 0) {
                throw new IllegalArgumentException("Product quantity must be greater than zero.");
            }


            ProductModel product = productModelRepository.findById(cartRequest.productId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));


            if (product.getQuantity() < cartRequest.quantity()) {
                throw new IllegalArgumentException("Insufficient stock for product " + product.getName());
            }
        }
    }
}
