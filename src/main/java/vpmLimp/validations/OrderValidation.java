package vpmLimp.validations;

import org.springframework.stereotype.Component;
import vpmLimp.DTO.OrderRequest;
import vpmLimp.model.ProductModel;

@Component
public class OrderValidation {

    public void validateProductStock(ProductModel product, int requestedQuantity) {
        if (product.getQuantity() < requestedQuantity) {
            throw new RuntimeException("Insufficient quantity for product: " + product.getName());
        }
    }

    public void validateOrderRequest(OrderRequest request) {
        if (request == null || request.productOrders().isEmpty()) {
            throw new IllegalArgumentException("Order request cannot be null or empty.");
        }

        request.productOrders().forEach(productOrder -> {
            if (productOrder.productId() == null || productOrder.quantity() <= 0) {
                throw new IllegalArgumentException("Invalid product order details.");
            }
        });
    }
}
