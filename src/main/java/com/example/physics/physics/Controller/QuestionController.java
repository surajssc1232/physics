package com.example.physics.physics.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.physics.physics.Entity.Questions;
import com.example.physics.physics.Service.QService;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    @Autowired
    private QService questionService;

    @GetMapping
    public List<Questions> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @PostMapping
    public Questions createQuestion(@RequestBody Questions question) {
        return questionService.createQuestion(question);
    }

    @PutMapping("/{id}")
    public Questions updateQuestion(@PathVariable Long id, @RequestBody Questions questionDetails) {
        return questionService.updateQuestion(id, questionDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
    }

    @GetMapping("/category")
    public List<Questions> getQuestionsByCategory(@RequestParam String category) {
        return questionService.getQuestionsByCategory(category);
    }
}