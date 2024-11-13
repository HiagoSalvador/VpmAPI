package vpmLimp.model;

import jakarta.persistence.*;
import lombok.*;
import vpmLimp.model.enums.CategoryProduct;

import java.util.List;

@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false, length = 100, unique = true)
    private String name;

    @Column(name = "description", nullable = false, length = 350)
    private String description;

    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToMany(mappedBy = "products")
    private List<OrderModel> orders;


    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoryProduct", nullable = false, length = 50)
    private CategoryProduct categoryProduct;

    @Column(name = "image_url")
    private String imageUrl;


}
