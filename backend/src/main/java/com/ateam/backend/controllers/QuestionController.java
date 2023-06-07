package com.ateam.backend.controllers;

import com.ateam.backend.exceptions.QuestionNotFoundException;
import com.ateam.backend.exceptions.QuizNotFoundException;
import com.ateam.backend.models.quiz.Question;
import com.ateam.backend.models.quiz.QuestionType;
import com.ateam.backend.models.quiz.Quiz;
import com.ateam.backend.services.QuestionService;
import com.ateam.backend.services.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/quiz")
public class QuestionController {
    private final QuizService quizService;
    private final QuestionService questionService;

    public QuestionController(QuizService quizService, QuestionService questionService) {
        this.quizService = quizService;
        this.questionService = questionService;
    }

    public String createQuestion(Principal principal) {
         String authorName = principal.getName();
         return "/create";
    }

    @PostMapping("/{id}/questions")
    public ResponseEntity<String> register(@PathVariable Long id, @RequestBody Question question) {
        Optional<Quiz> quiz = quizService.findById(id);
        if (quiz.isEmpty()) {
            throw new QuizNotFoundException();
        }

        if (!isPostDataValid(question.getTitle(), question.getDescription(), question.getType())) {
            return ResponseEntity.badRequest().body("Error: Bad request - missing required data!");
        }

        boolean hasSingleCorrectAnswer;
        switch (question.getType()) {
            case SINGLE_CHOICE:
                hasSingleCorrectAnswer = true;
                break;
            case MULTIPLE_CHOICE:
                hasSingleCorrectAnswer = false;
                break;
            case FILL_OUT:
                hasSingleCorrectAnswer = true;
                break;
            case PAIR_ASSIGN:
                hasSingleCorrectAnswer = true;
                break;
            default:
                return ResponseEntity.badRequest().body("Error: Invalid question type!");
        }

        Question newQuestion = new Question(
                null,
                quiz.get(),
                question.getType(),
                question.getTitle(),
                question.getDescription(),
                hasSingleCorrectAnswer
        );

        questionService.save(newQuestion);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{quiz_id}/questions/{question_id}/modify")
    public ResponseEntity modify(@PathVariable Long quiz_id,
                                 @PathVariable Long question_id,
                                 @RequestBody Question question,
                                 Principal principal) {
        Optional<Quiz> myQuiz = quizService.findById(quiz_id);
        Optional<Question> myQuestion = questionService.findById(question_id);

        if(!principal.getName().equals(myQuiz.get().getAuthor().getUsername())){
            return ResponseEntity.badRequest().body("You have no rights to do this changes!");
        }

        if(!myQuiz.isPresent()){
            throw new QuizNotFoundException();
        }

        if(!myQuestion.isPresent()){
            throw new QuestionNotFoundException();
        }

        if(!isPostDataValid(question.getTitle(), question.getDescription(), question.getType())) {
            return ResponseEntity.badRequest().body("Required data are missing!");
        }

        Question modifyQuestion = new Question(
                    question_id,
                    myQuestion.get().getQuiz_id(),
                    question.getType(),
                    question.getTitle(),
                    question.getDescription(),
                question.isHasSingleCorrectAnswer()
        );

        questionService.save(modifyQuestion);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{quiz_id}/questions/{question_id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Long quiz_id, @PathVariable Long question_id, Principal principal) {
        Optional<Quiz> quiz = quizService.findById(quiz_id);
        Optional<Question> question = questionService.findById(question_id);

        if (!quiz.isPresent()) {
            throw new QuizNotFoundException();
        }

        if (!question.isPresent()) {
            throw new QuestionNotFoundException();
        }

        if (!principal.getName().equals(quiz.get().getAuthor().getUsername())) {
            return ResponseEntity.badRequest().body("You have no rights to delete this question!");
        }

        Question q = question.get();
        q.setDeleted(true);
        questionService.save(q);

        return ResponseEntity.ok().body("Question deleted!");
    }

    private boolean isPostDataValid(String title,
                                        String description,
                                        QuestionType type) {
            return (isStringValid(title) && isStringValid(description) && type != null);
    }
    private boolean isStringValid(String s) {
                return (s != null && !s.isEmpty());
        }

    private String getStoreQuestionErrorResponse(RedirectAttributes redirectAttributes, Question question, String errorMessage) {
                redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
                redirectAttributes.addFlashAttribute("question", question);
                return "redirect:/questions/create";
    }
}