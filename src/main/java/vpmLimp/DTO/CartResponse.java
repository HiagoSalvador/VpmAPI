package vpmLimp.DTO;

public record CartResponse(Long productId, int quantity, java.math.BigDecimal totalPrice) {
}