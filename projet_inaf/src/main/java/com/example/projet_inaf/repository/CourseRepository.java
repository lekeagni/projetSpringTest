package com.example.projet_inaf.repository;

import com.example.projet_inaf.model.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<CourseModel,Integer> {
    Optional<CourseModel> findByTitle(String title);
}
