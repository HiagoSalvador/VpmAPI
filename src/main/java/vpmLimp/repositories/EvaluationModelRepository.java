package vpmLimp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vpmLimp.model.EvaluationModel;

@Repository
public interface EvaluationModelRepository extends JpaRepository<EvaluationModel, Long> {
}