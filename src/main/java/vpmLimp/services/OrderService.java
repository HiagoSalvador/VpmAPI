package vpmLimp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import vpmLimp.DTO.OrderRequest;
import vpmLimp.DTO.OrderResponse;
import vpmLimp.model.OrderModel;
import vpmLimp.model.ProductModel;
import vpmLimp.model.UserModel;
import vpmLimp.model.enums.OrderStatus;
import vpmLimp.repositories.OrderModelRepository;
import vpmLimp.repositories.ProductModelRepository;
import vpmLimp.repositories.UserModelRepository;
import vpmLimp.validations.OrderValidation;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderModelRepository orderRepository;

    @Autowired
    private UserModelRepository userRepository;

    @Autowired
    private ProductModelRepository productRepository;

    @Autowired
    private OrderValidation orderValidation;

    public OrderResponse createOrder(OrderRequest orderRequest) {
        orderValidation.validateOrderRequest(orderRequest);

        UserModel user = getAuthenticatedUser();

        List<ProductModel> products = orderRequest.productOrders().stream()
                .map(productOrder -> {
                    ProductModel product = productRepository.findById(productOrder.productId())
                            .orElseThrow(() -> new RuntimeException("Product not found: " + productOrder.productId()));

                    orderValidation.validateProductStock(product, productOrder.quantity());

                    product.setQuantity(product.getQuantity() - productOrder.quantity());
                    return product;
                })
                .collect(Collectors.toList());

        double totalAmount = products.stream()
                .mapToDouble(product -> product.getPrice() *
                        orderRequest.productOrders().stream()
                                .filter(p -> p.productId().equals(product.getId()))
                                .findFirst()
                                .orElseThrow()
                                .quantity()
                )
                .sum();

        OrderModel order = new OrderModel();
        order.setUser(user);
        order.setProducts(products);
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(totalAmount);
        orderRepository.save(order);

        return convertToOrderResponse(order);
    }

    public List<OrderResponse> getUserOrders(Long userId) {
        List<OrderModel> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(this::convertToOrderResponse)
                .collect(Collectors.toList());
    }

    public void cancelOrder(Long orderId) {
        UserModel authenticatedUser = getAuthenticatedUser();
        OrderModel order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

        if (!order.getUser().getEmail().equals(authenticatedUser.getEmail())) {
            throw new RuntimeException("You are not authorized to cancel this order.");
        }

        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }

    private UserModel getAuthenticatedUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String authenticatedCPF = userDetails.getUsername();
        return userRepository.findByCpf(authenticatedCPF)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
    }

    private OrderResponse convertToOrderResponse(OrderModel order) {
        return new OrderResponse(
                order.getId(),
                order.getStatus().name(),
                order.getUser().getAddress(),
                order.getUser().getCity(),
                order.getProducts().stream()
                        .map(product -> new OrderResponse.ProductDetails(
                                product.getId(),
                                product.getName(),
                                product.getDescription(),
                                product.getPrice(),
                                product.getQuantity()
                        ))
                        .collect(Collectors.toList()),
                order.getTotalAmount()
        );
    }
}
