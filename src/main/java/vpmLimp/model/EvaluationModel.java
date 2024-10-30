package vpmLimp.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "evaluations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EvaluationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int rating;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;


}
