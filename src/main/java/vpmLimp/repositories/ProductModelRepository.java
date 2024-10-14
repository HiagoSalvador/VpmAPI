package vpmLimp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vpmLimp.model.ProductModel;

import java.util.Optional;

@Repository
public interface ProductModelRepository extends JpaRepository<ProductModel, Long> {

}