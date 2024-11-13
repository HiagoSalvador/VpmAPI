package vpmLimp.DTO;

import lombok.Data;
import vpmLimp.model.ProductModel;
import vpmLimp.model.enums.CategoryProduct;

@Data
public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private int quantity;
    private CategoryProduct categoryProduct;
    private String imageUrl;

    public ProductResponse(Long id, String name, String description, Double price, int quantity, CategoryProduct categoryProduct, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.categoryProduct = categoryProduct;
        this.imageUrl = imageUrl;
    }

    public ProductResponse(Long id, String name, String description, Double price, int quantity, CategoryProduct categoryProduct) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.categoryProduct = categoryProduct;
    }

    public ProductResponse(ProductModel product) {
        this(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getQuantity(), product.getCategoryProduct(), product.getImageUrl());
    }


}
