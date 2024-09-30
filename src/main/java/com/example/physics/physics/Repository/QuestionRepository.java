package com.example.physics.physics.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.physics.physics.Entity.Questions;

public interface QuestionRepository extends JpaRepository<Questions, Long> {
    List<Questions> findByCategory(String category);
}