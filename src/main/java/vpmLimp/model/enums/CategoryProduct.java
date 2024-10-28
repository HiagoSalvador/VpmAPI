package vpmLimp.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CategoryProduct {

    CASA("CASA"),
    CONSTRUCAO("CONSTRUCAO");

    private final String value;

    CategoryProduct(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static CategoryProduct fromValue(String value) {
        for (CategoryProduct category : CategoryProduct.values()) {
            if (category.value.equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Category not exists: " + value);
    }
}