package vpmLimp.DTO;

import java.util.List;

public record OrderRequest(List<ProductOrder> productOrders) {
}
