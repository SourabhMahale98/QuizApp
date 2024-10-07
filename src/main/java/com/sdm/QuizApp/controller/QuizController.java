package com.sdm.QuizApp.controller;

import com.sdm.QuizApp.model.QuestionWrapper;
import com.sdm.QuizApp.model.Quiz;
import com.sdm.QuizApp.model.UserResponse;
import com.sdm.QuizApp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int noQuestion, @RequestParam String title){
        return quizService.createQuiz(category,noQuestion,title);
    }

    @GetMapping("getQuiz/{quizId}")
    public ResponseEntity<List<QuestionWrapper>> fetchQuiz(@PathVariable Integer quizId){
        return quizService.fetchQuiz(quizId);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<String> submitQuiz(@PathVariable Integer id,@RequestBody List<UserResponse> responses){
            return  quizService.calculateResult(id,responses);
    }
}
