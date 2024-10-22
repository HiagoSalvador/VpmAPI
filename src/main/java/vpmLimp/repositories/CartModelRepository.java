package vpmLimp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vpmLimp.model.CartModel;

import java.util.Optional;

@Repository
public interface CartModelRepository extends JpaRepository<CartModel, Long> {

    Optional<CartModel> findByUserId(Long userId);

}