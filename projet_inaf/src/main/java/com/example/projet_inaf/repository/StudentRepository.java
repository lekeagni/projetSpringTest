package com.example.projet_inaf.repository;

import com.example.projet_inaf.model.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentModel, Integer> {
    Optional<StudentModel> findByName(String name);
}
