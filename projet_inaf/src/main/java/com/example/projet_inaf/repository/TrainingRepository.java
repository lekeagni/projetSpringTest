package com.example.projet_inaf.repository;

import com.example.projet_inaf.model.TrainingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainingRepository extends JpaRepository<TrainingModel, Integer> {

    Optional<TrainingModel> findByName(String name);
}
