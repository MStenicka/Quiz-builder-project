package com.ateam.backend.controllers;

import com.ateam.backend.exceptions.AnswerNotFoundException;
import com.ateam.backend.exceptions.QuestionNotFoundException;
import com.ateam.backend.exceptions.QuizNotFoundException;
import com.ateam.backend.models.quiz.Answer;
import com.ateam.backend.models.quiz.AnswerToModifyDto;
import com.ateam.backend.models.quiz.Question;
import com.ateam.backend.models.quiz.Quiz;
import com.ateam.backend.repositories.AnswerRepository;
import com.ateam.backend.services.AnswerService;
import com.ateam.backend.services.QuestionService;
import com.ateam.backend.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class AnswerController {

    @Autowired
    private final AnswerService answerService;
    private final QuestionService questionService;

    private final QuizService quizService;

    private final AnswerRepository answerRepository;

    public AnswerController(AnswerService answerService, QuestionService questionService, QuizService quizService, AnswerRepository answerRepository) {
        this.answerService = answerService;
        this.questionService = questionService;
        this.quizService = quizService;
        this.answerRepository = answerRepository;
    }

    // Soft delete an answer by ID
    @DeleteMapping("answers/{id}")
    public ResponseEntity<String> deleteAnswer(@PathVariable Long id) {
        Answer answer = answerService.findById(id);
        if (answer == null) {
            throw new AnswerNotFoundException();
        }
        answer.setDeleted(true);
        answerService.save(answer);
        return  ResponseEntity.ok().body("Answer deleted!");
    }

    @GetMapping("/answers")
    public List<Answer> getUndeletedAnswers() {
        return answerService.findByDeletedFalse();
    }

    @PostMapping("/{quiz_id}/questions/{question_id}/answer")
    public ResponseEntity<String> createAnswer(@PathVariable Long quiz_id,
                                               @PathVariable Long question_id,
                                               @RequestBody List<Answer> answers) {
        System.out.println(quiz_id);
        System.out.println(question_id);
        System.out.println(answers);
        Optional<Question> question = questionService.findById(question_id);

        if (!question.isPresent()) {
            throw new QuizNotFoundException();
        }

        for (Answer answer : answers) {
            switch (question.get().getType()) {
                case SINGLE_CHOICE:
                    long correctAnswerCount = answers.stream().filter(Answer::isCorrect).count();
                    if (correctAnswerCount != 1) {
                        return ResponseEntity.badRequest().body("Error: Only one answer can be correct for a single choice question!");
                    }
                    break;
                case MULTIPLE_CHOICE:
                    correctAnswerCount = answers.stream().filter(Answer::isCorrect).count();
                    if (correctAnswerCount < 2 || correctAnswerCount == answers.size()) {
                        return ResponseEntity.badRequest().body("Error: At least two and at most all answers must be marked as correct for a multiple choice question!");
                    }
                    break;
                case FILL_OUT:
                    if (answer.getDescription().equalsIgnoreCase(question.get().getDescription())) {
                        answer.setCorrect(true);
                        question.get().setCorrectAnswerIds(Collections.singletonList(answer.getId())); // set correct answer ID
                        question.get().setTitle(answer.getTitle().toString()); // update question title

                    } else {
                        answer.setCorrect(false);
                        return ResponseEntity.badRequest().body("Error: Incorrect answer for a fill-in question!");
                    }
                    break;
                case PAIR_ASSIGN:
                    List<String> usePairs = Arrays.asList(answer.getDescription().split(";"));
                    for (String usePair : usePairs) {
                        if (answer.getDescription().equals(question.get().getDescription())) {
                            answer.setCorrect(true);
                        } else {
                            answer.setCorrect(false);
                            return ResponseEntity.badRequest().body("Error: Incorrect pairs for a pair assign question!");
                        }
                    }
                    break;
                default:
                    break;
            }

            if (!isPostDataValid(answer.getDescription())) {
                return ResponseEntity.badRequest().body("Error: Required data are missing!");
            }

            Answer newAnswer = new Answer(
                    null,
                    question.get(),
                    answer.getTitle(),
                    answer.getDescription(),
                    answer.isCorrect()
            );
            newAnswer.setDeleted(false);
            answerService.save(newAnswer);

            if (answer.isCorrect()) {
                question.get().addCorrectAnswerId(newAnswer.getId());
            }
        }
        return ResponseEntity.ok().build();
    }

    private boolean isPostDataValid(String description) {
    return (description != null && !description.isEmpty());

    }



    @PatchMapping("/{quiz_id}/questions/{question_id}/answers/{answer_id}/modify")
    public ResponseEntity modify(@PathVariable Long quiz_id,
                                 @PathVariable Long question_id,
                                 @PathVariable Long answer_id,
                                 @RequestBody AnswerToModifyDto answerToModifyDto) {
        Optional<Quiz> myQuiz = quizService.findById(quiz_id);
        Optional<Question> myQuestion = questionService.findById(question_id);
        Optional <Answer> myAnswer = Optional.ofNullable(answerService.findById(answer_id));

        if(!myQuiz.isPresent()){
            throw new QuizNotFoundException();
        }

        if(!myQuestion.isPresent()){
            throw new QuestionNotFoundException();
        }

        if(!myAnswer.isPresent()){
            throw new AnswerNotFoundException();
        }

        if (!isPostDataValidToModify(answerToModifyDto.getTitle(), answerToModifyDto.getDescription(), answerToModifyDto.isCorrect())) {
            return ResponseEntity.badRequest().body("Error: Required data are missing!");
        }

        Answer modifiedAnswer = new Answer(
                answer_id,
                myAnswer.get().getQuestion_id(),
                answerToModifyDto.getTitle(),
                answerToModifyDto.getDescription(),
                answerToModifyDto.isCorrect()
        );

        answerRepository.save(modifiedAnswer);

        return ResponseEntity.ok().build();
    }
    private boolean isPostDataValidToModify(String title,
                                    String description,
                                    boolean isCorrect) {
        return(isStringValid(title) && isStringValid(description));
    }
    private boolean isStringValid(String s) {
        return (s != null && !s.isEmpty());
    }
}
