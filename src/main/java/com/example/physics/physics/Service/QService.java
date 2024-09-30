package com.example.physics.physics.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.physics.physics.Entity.Questions;
import com.example.physics.physics.Repository.QuestionRepository;

@Service
public class QService {
    @Autowired
    private QuestionRepository questionRepository;

    // Method to get all questions
    public List<Questions> getAllQuestions() {
        return questionRepository.findAll();
    }

    // Method to create a new question
    public Questions createQuestion(Questions question) {
        return questionRepository.save(question);
    }

    // Method to update a question
    public Questions updateQuestion(Long id, Questions questionDetails) {
        Questions question = questionRepository.findById(id).orElseThrow(() -> new RuntimeException("Question not found"));
        question.setContent(questionDetails.getContent());
        question.setSolution(questionDetails.getSolution());
        question.setCategory(questionDetails.getCategory()); // Update category
        return questionRepository.save(question);
    }

    // Method to delete a question
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    // Method to get questions by category
    public List<Questions> getQuestionsByCategory(String category) {
        return questionRepository.findByCategory(category);
    }
}