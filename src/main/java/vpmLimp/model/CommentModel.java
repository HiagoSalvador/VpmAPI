package vpmLimp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name ="comment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;


    private String commentText;
}
