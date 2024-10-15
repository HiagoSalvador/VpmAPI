package vpmLimp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vpmLimp.model.OrderModel;

import java.util.List;

@Repository
public interface OrderModelRepository extends JpaRepository<OrderModel, Long> {

    List<OrderModel> findByUserId(Long userId);
}
