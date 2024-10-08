package vpmLimp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vpmLimp.model.CommentModel;

@Repository
public interface CommentModelRepository extends JpaRepository<CommentModel, Long> {
}