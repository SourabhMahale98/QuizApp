package com.sdm.QuizApp.service;

import com.sdm.QuizApp.dao.QuestionDao;
import com.sdm.QuizApp.dao.QuizDao;
import com.sdm.QuizApp.model.Question;
import com.sdm.QuizApp.model.QuestionWrapper;
import com.sdm.QuizApp.model.Quiz;
import com.sdm.QuizApp.model.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int noQuestion, String title) {

        List<Question> questions = questionDao.findRandomQuestionByCategory(category,noQuestion);
        System.out.println(questions.size());
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        System.out.println(quiz);
        quizDao.save(quiz);

        return new ResponseEntity<>(title + " Quiz is created ", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> fetchQuiz(Integer quizId) {
            Optional<Quiz> quiz = quizDao.findById(quizId);
            List<Question> questionsFromDB = quiz.get().getQuestions();

            List<QuestionWrapper> questionsForUser = new ArrayList<>();

            for(Question q : questionsFromDB){
                QuestionWrapper qw = new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
                questionsForUser.add(qw);
            }

            return new ResponseEntity<>(questionsForUser,HttpStatus.OK);
    }

    public ResponseEntity<String> calculateResult(Integer id, List<UserResponse> responses) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questions = quiz.get().getQuestions();
        System.out.println("Questions  : "+ questions);
        int rightAns= 0;
        int i=0;
        for(UserResponse userResponse : responses){
            for(Question question : questions){
                if(question.getId().equals(userResponse.getId())){

                    if(userResponse.getResponse().equals(question.getQuestionAns())){
                         rightAns++;
                        }
                }
            }
//            if(userResponse.getResponse().equals(questions.get(i).getQuestionAns())){
//                rightAns++;
//            }
//            i++;
        }
        return new ResponseEntity<>("You got "+rightAns+ " questions right",HttpStatus.OK);
    }
}
