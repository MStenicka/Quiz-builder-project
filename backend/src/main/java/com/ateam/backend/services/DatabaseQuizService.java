package com.ateam.backend.services;

import com.ateam.backend.exceptions.QuizNotFoundException;
import com.ateam.backend.models.quiz.*;
import com.ateam.backend.repositories.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
 public class DatabaseQuizService implements QuizService{

    private QuizRepository quizRepository;

    public DatabaseQuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Override
    public List<Quiz> findByDeletedFalse() {
        return quizRepository.findByDeletedFalse();
    }

    @Override
    public Optional<Quiz> findById(Long id) {
        return quizRepository.findById(id);
    }

    @Override
    public List<Quiz> getQuizzesByAuthor(String author) {
        return quizRepository.findByAuthor(author);
    }

    @Override
    public List<Quiz> getPublicAndEnabledQuizzesWithoutAuthorQuizzes(QuizVisibility visibility, QuizStatus status, String author) {
        return quizRepository.findByVisibilityAndStatusAndAuthorNot(QuizVisibility.PUBLIC, QuizStatus.ENABLED, author);
    }

    public QuizDetailsDto getQuizDetails(Long id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        if(!quiz.isPresent()){
            throw new QuizNotFoundException();
        }

        QuizDetailsDto quizDetails = new QuizDetailsDto();
        quizDetails.setId(quiz.get().getId());
        quizDetails.setTitle(quiz.get().getTitle());

        List<QuestionDetailsDto> questionDetailsList = new ArrayList<>();
        for(Question question : quiz.get().getQuestions()) {
            QuestionDetailsDto questionDetails = new QuestionDetailsDto();
            questionDetails.setId(question.getId());
            questionDetails.setQuestion(question.getDescription());
            questionDetails.setAnswers(question.getAnswers());

            if(quiz.get().getIsAuthor()) {
                for(Answer answer : question.getAnswers()) {
                    questionDetails.setCorrectAnswer(answer.isCorrect());
                }
            }
            questionDetailsList.add(questionDetails);
        }
        quizDetails.setQuestionDetails(questionDetailsList);

        return quizDetails;
    }

    @Override
    public Quiz save(Quiz quiz) {
        return quizRepository.save(quiz);
    }
}

