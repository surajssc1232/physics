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

    
    public List<Questions> getAllQuestions() {
        return questionRepository.findAll();
    }

    
    public Questions createQuestion(Questions question) {
        return questionRepository.save(question);
    }

    
    public Questions updateQuestion(Long id, Questions questionDetails) {
        Questions question = questionRepository.findById(id).orElseThrow(() -> new RuntimeException("Question not found"));
        question.setContent(questionDetails.getContent());
        question.setSolution(questionDetails.getSolution());
        question.setCategory(questionDetails.getCategory()); 
        return questionRepository.save(question);
    }

    
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    
    public List<Questions> getQuestionsByCategory(String category) {
        return questionRepository.findByCategory(category);
    }
}