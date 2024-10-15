package vpmLimp.model;

import jakarta.persistence.*;
import lombok.*;

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

    /*
    TODO: Implementar Imagem - Foto do produto
    @Lob
    @Column(name = "image" )
    private byte[] image;
     */









}
