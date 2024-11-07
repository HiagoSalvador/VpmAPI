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

import vpmLimp.validations.CartValidation;

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

    private final CartValidation cartValidation;

    @Autowired
    public CartService(ProductModelRepository productModelRepository) {
        this.productModelRepository = productModelRepository;
        this.cartValidation = new CartValidation(productModelRepository);
    }

    public Map<String, Object> addProductsToCart(List<CartRequest> cartRequests) {
        cartValidation.validateCartRequests(cartRequests);

        CartModel cart = (CartModel) httpSession.getAttribute("cart");
        if (cart == null) {
            cart = new CartModel();
            cart.setProducts(new ArrayList<>());
            httpSession.setAttribute("cart", cart);
        }

        List<CartResponse> responses = new ArrayList<>();

        for (CartRequest cartRequest : cartRequests) {
            ProductModel product = findProductById(cartRequest.productId());

            // Removido o bloqueio do estoque insuficiente
            // Verifica se o produto já existe no carrinho
            Optional<ProductModel> existingProduct = cart.getProducts().stream()
                    .filter(p -> p.getId().equals(product.getId()))
                    .findFirst();

            if (existingProduct.isPresent()) {
                existingProduct.get().setQuantity(existingProduct.get().getQuantity() + cartRequest.quantity());
            } else {
                // Adiciona o produto com a quantidade solicitada
                product.setQuantity(cartRequest.quantity());
                cart.getProducts().add(product);
            }
        }

        cartRepository.save(cart);

        BigDecimal totalCartPrice = calculateTotalPrice(cart.getProducts());

        for (ProductModel product : cart.getProducts()) {
            BigDecimal productTotalPrice = BigDecimal.valueOf(product.getPrice())
                    .multiply(BigDecimal.valueOf(product.getQuantity()))
                    .setScale(2, RoundingMode.HALF_UP);

            responses.add(new CartResponse(product.getId(), product.getQuantity(), productTotalPrice));
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("products", responses);
        result.put("totalPrice", totalCartPrice.setScale(2, RoundingMode.HALF_UP));

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

    private ProductModel findProductById(Long productId) {
        return productModelRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
    }

    public Map<String, Object> getCart() {
        CartModel cart = (CartModel) httpSession.getAttribute("cart");

        if (cart == null || cart.getProducts() == null || cart.getProducts().isEmpty()) {
            throw new RuntimeException("No products found in the cart for the current session.");
        }

        List<CartResponse> responses = new ArrayList<>();
        BigDecimal totalCartPrice = BigDecimal.ZERO;

        for (ProductModel product : cart.getProducts()) {
            BigDecimal productTotalPrice = BigDecimal.valueOf(product.getPrice())
                    .multiply(BigDecimal.valueOf(product.getQuantity()))
                    .setScale(2, RoundingMode.HALF_UP);

            totalCartPrice = totalCartPrice.add(productTotalPrice);

            responses.add(new CartResponse(product.getId(), product.getQuantity(), productTotalPrice));
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("products", responses);
        result.put("totalPrice", totalCartPrice.setScale(2, RoundingMode.HALF_UP));

        return result;
    }
}
