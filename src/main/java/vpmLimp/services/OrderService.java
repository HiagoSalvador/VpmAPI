package vpmLimp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import vpmLimp.DTO.OrderResponse;
import vpmLimp.model.OrderModel;
import vpmLimp.model.ProductModel;
import vpmLimp.model.UserModel;
import vpmLimp.model.enums.OrderStatus;
import vpmLimp.repositories.OrderModelRepository;
import vpmLimp.repositories.ProductModelRepository;
import vpmLimp.repositories.UserModelRepository;

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

    public List<String> createOrder(List<Long> productIds) {
        UserModel user = getAuthenticatedUser();
        List<ProductModel> products = productRepository.findAllById(productIds);
        OrderModel order = new OrderModel();
        order.setUser(user);
        order.setProducts(products);
        order.setStatus(OrderStatus.PENDING);
        orderRepository.save(order);
        return products.stream()
                .map(ProductModel::getName)
                .collect(Collectors.toList());
    }

    public List<OrderResponse> getUserOrders(Long userId) {
        List<OrderModel> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(this::convertToOrderResponse)
                .collect(Collectors.toList());
    }

    private UserModel getAuthenticatedUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String authenticatedEmail = userDetails.getUsername();
        return userRepository.findByEmail(authenticatedEmail)
                .orElseThrow(() -> new RuntimeException("User autenticated not found"));
    }

    private OrderResponse convertToOrderResponse(OrderModel order) {
        return new OrderResponse(
                order.getId(),
                order.getStatus(),
                order.getUser().getAddress(),
                order.getUser().getCity(),
                order.getProducts().stream()
                        .map(product -> new OrderResponse.ProductDetails(
                                product.getId(),
                                product.getName(),
                                product.getDescription(),
                                product.getPrice()
                        ))
                        .collect(Collectors.toList())
        );
    }
}
