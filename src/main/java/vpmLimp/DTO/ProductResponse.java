package vpmLimp.DTO;

import lombok.*;

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





}
