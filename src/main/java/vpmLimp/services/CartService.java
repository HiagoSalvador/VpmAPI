package vpmLimp.services;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import vpmLimp.DTO.CartRequest;
import vpmLimp.DTO.CartResponse;
import vpmLimp.model.CartModel;
import vpmLimp.model.ProductModel;
import vpmLimp.repositories.CartModelRepository;
import vpmLimp.repositories.ProductModelRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@SessionScope
public class CartService {

    @Autowired
    private ProductModelRepository productModelRepository;

    @Autowired
    private CartModelRepository cartRepository;

    @Autowired
    private HttpSession httpSession;

    public Map<String, Object> addProductsToCart(List<CartRequest> cartRequests) {
        validateCartRequests(cartRequests);

        CartModel cart = (CartModel) httpSession.getAttribute("cart");
        if (cart == null) {
            cart = new CartModel();
            cart.setProducts(new ArrayList<>());
            httpSession.setAttribute("cart", cart);
        }

        List<CartResponse> responses = new ArrayList<>();

        for (CartRequest cartRequest : cartRequests) {
            ProductModel product = findProductById(cartRequest.productId());


            if (product.getQuantity() < cartRequest.quantity()) {
                throw new IllegalArgumentException("Insufficient stock for product " + product.getName());
            }

            Optional<ProductModel> existingProduct = cart.getProducts().stream()
                    .filter(p -> p.getId().equals(product.getId()))
                    .findFirst();

            if (existingProduct.isPresent()) {
                existingProduct.get().setQuantity(existingProduct.get().getQuantity() + cartRequest.quantity());
            } else {
                product.setQuantity(cartRequest.quantity());
                cart.getProducts().add(product);
            }
        }

        cartRepository.save(cart);


        BigDecimal totalPrice = calculateTotalPrice(cart.getProducts());


        for (ProductModel product : cart.getProducts()) {
            responses.add(new CartResponse(product.getId(), product.getQuantity(), totalPrice));
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("products", responses);
        result.put("totalPrice", totalPrice.setScale(2, RoundingMode.HALF_UP));

        return result;
    }

    public void removeProductsFromCart(List<Long> productIdsToRemove) {
        if (productIdsToRemove == null || productIdsToRemove.isEmpty()) {
            throw new IllegalArgumentException("The list of products for removal cannot be empty.");
        }

        CartModel cart = (CartModel) httpSession.getAttribute("cart");
        if (cart == null || cart.getProducts() == null) {
            throw new RuntimeException("No cart found for the current session.");
        }

        if (cart.getProducts().isEmpty()) {
            throw new RuntimeException("The cart is already empty.");
        }

        for (Long productId : productIdsToRemove) {
            Optional<ProductModel> productToRemove = cart.getProducts().stream()
                    .filter(p -> p.getId().equals(productId))
                    .findFirst();

            if (productToRemove.isPresent()) {
                cart.getProducts().remove(productToRemove.get());
            } else {
                throw new RuntimeException("Product not found in cart");
            }
        }

        if (cart.getProducts().isEmpty()) {
            httpSession.removeAttribute("cart");
        } else {
            cartRepository.save(cart);
        }
    }

    private BigDecimal calculateTotalPrice(List<ProductModel> products) {
        return products.stream()
                .map(p -> BigDecimal.valueOf(p.getPrice()).multiply(BigDecimal.valueOf(p.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private void validateCartRequests(List<CartRequest> cartRequests) {
        if (cartRequests == null || cartRequests.isEmpty()) {
            throw new IllegalArgumentException("The list of products for the cart cannot be empty.");
        }

        cartRequests.forEach(cartRequest -> {
            if (cartRequest.productId() == null) {
                throw new IllegalArgumentException("Product not reconheced.");
            }
            if (cartRequest.quantity() <= 0) {
                throw new IllegalArgumentException("Product quantity must be greater than zero.");
            }
        });
    }

    private ProductModel findProductById(Long productId) {
        return productModelRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
    }
}