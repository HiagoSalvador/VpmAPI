package vpmLimp.DTO;


import vpmLimp.model.enums.CategoryProduct;

public record ProductRequest(String name, String description, Double price, int quantity, CategoryProduct category) {
}
