package vpmLimp.DTO;

import vpmLimp.model.ProductModel;
import vpmLimp.model.enums.CategoryProduct;

public record ProductResponse(Long id, String name, String description, Double price, int quantity, CategoryProduct category) {

    public ProductResponse(ProductModel product) {
        this(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getQuantity(), product.getCategoryProduct());
    }

}
