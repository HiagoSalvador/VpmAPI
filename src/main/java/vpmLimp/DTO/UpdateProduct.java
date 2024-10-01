package vpmLimp.DTO;

import jakarta.persistence.Column;
import lombok.Value;

@Value
public class UpdateProduct {

    private String name;


    private String description;


    private Double price;


}
