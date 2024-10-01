package vpmLimp.DTO;

import lombok.*;
import vpmLimp.model.ProductModel;

@Value
public class ProductResponse {


    private Long id;
    private String name;
    private String description;
    private Double price;
  



    public ProductResponse(Long id, String name, String description, Double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;

    }


    public ProductResponse(ProductModel product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.description = product.getDescription();
    }



}
