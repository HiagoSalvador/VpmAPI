package vpmLimp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vpmLimp.model.UserModel;


import java.util.Optional;

@Repository
public interface UserModelRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByEmail(String email);



}