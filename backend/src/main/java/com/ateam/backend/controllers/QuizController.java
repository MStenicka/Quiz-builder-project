package com.ateam.backend.controllers;

import com.ateam.backend.exceptions.QuizNotFoundException;
import com.ateam.backend.models.appUser.AppUser;
import com.ateam.backend.models.quiz.*;
import com.ateam.backend.repositories.QuizRepository;
import com.ateam.backend.services.JpaUserDetailsService;
import com.ateam.backend.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizRepository quizRepository;

    private final QuizService quizService;

    private final JpaUserDetailsService jpaUserDetailsService;
    @Autowired
    public QuizController(QuizRepository quizRepository, QuizService quizService, JpaUserDetailsService jpaUserDetailsService) {
        this.quizRepository = quizRepository;
        this.quizService = quizService;
        this.jpaUserDetailsService = jpaUserDetailsService;
    }

    @GetMapping({"","/"})
    public Object getQuizzes(Principal principal) {
        String authorName = principal.getName();

        List<Quiz> authorQuizzes = quizService.getQuizzesByAuthor(authorName);
        List<Quiz> publicAndEnabledQuizzesWithoutAuthorQuizzes = quizService.getPublicAndEnabledQuizzesWithoutAuthorQuizzes(QuizVisibility.PUBLIC, QuizStatus.ENABLED, authorName);
        return new QuizzesResponse(authorQuizzes, publicAndEnabledQuizzesWithoutAuthorQuizzes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizDetailsDto> getQuizDetails(@PathVariable Long id) {
        QuizDetailsDto quizDetails = quizService.getQuizDetails(id);
        return ResponseEntity.ok(quizDetails);
    }

    @PostMapping("/create_quiz")
    public ResponseEntity<String> store(@RequestBody QuizToCreateOrModifyDto quizData,
                                        Principal principal) throws IOException {

        Optional<AppUser> myUser = jpaUserDetailsService.findByUsernameOrEmail(principal.getName(), principal.getName());

        Quiz quiz = new Quiz();

        if(quizData.getAllowedUsersIds().isEmpty()) {
            quiz.setVisibility(QuizVisibility.PUBLIC);
        }

        List<AppUser> allowedUsers = new ArrayList<>();
        for(Long userID : quizData.getAllowedUsersIds()) {
            Optional<AppUser> user = jpaUserDetailsService.findById(userID);
            if(!user.isPresent() && !quizData.getAllowedUsersIds().isEmpty()) {
                return ResponseEntity.badRequest().body("User with ID:" + userID + " not found");
            }
            allowedUsers.add(user.get());
            quiz.setVisibility(QuizVisibility.PRIVATE);
        }

        if(!isPostDataValid(quizData.getTitle(), quizData.getDescription(), quizData.getStatusOfQuiz(), quizData.getGrading())){
            return ResponseEntity.badRequest().body("Error: Bad request - missing required data!");
        }

        quiz.setAllowedUsers(allowedUsers);

        Quiz newQuiz = new Quiz(
                null,
                quizData.getTitle(),
                quizData.getDescription(),
                quizData.getStatusOfQuiz(),
                quiz.getVisibility(),
                quizData.getGrading(),
                myUser.get(),
                true,
                quiz.getAllowedUsers()
        );

        quizRepository.save(newQuiz);

        return ResponseEntity.ok().build();
    }


    @PatchMapping("/{id}/modify")
    public ResponseEntity modify(@PathVariable Long id,
                                 @RequestBody QuizToCreateOrModifyDto quizToModifyDto,
                                 Principal principal) {
        Optional<Quiz> myQuiz = quizService.findById(id);

        if(!myQuiz.isPresent()){
            throw new QuizNotFoundException();
        }

        if(!principal.getName().equals(myQuiz.get().getAuthor().getUsername())){
            return ResponseEntity.badRequest().body("You have no rights to do this changes!");
        }

        if(quizToModifyDto.getAllowedUsersIds().isEmpty()) {
            myQuiz.get().setVisibility(QuizVisibility.PUBLIC);
        }

        List<AppUser> allowedUsers = new ArrayList<>();
        for(Long userID : quizToModifyDto.getAllowedUsersIds()) {
            Optional<AppUser> user = jpaUserDetailsService.findById(userID);
            if(!user.isPresent() && !quizToModifyDto.getAllowedUsersIds().isEmpty()) {
                return ResponseEntity.badRequest().body("User with ID:" + userID + " not found");
            }
            allowedUsers.add(user.get());
            myQuiz.get().setVisibility(QuizVisibility.PRIVATE);
        }

        if(!isPostDataValid(quizToModifyDto.getTitle(), quizToModifyDto.getDescription(), quizToModifyDto.getStatusOfQuiz(), quizToModifyDto.getGrading())){
            return ResponseEntity.badRequest().body("Error: Bad request - missing required data!");
        }

        myQuiz.get().setAllowedUsers(allowedUsers);

        Quiz modifiedQuiz = new Quiz(
                id,
                quizToModifyDto.getTitle(),
                quizToModifyDto.getDescription(),
                quizToModifyDto.getStatusOfQuiz(),
                quizToModifyDto.getVisibility(),
                quizToModifyDto.getGrading(),
                myQuiz.get().getAuthor(),
                myQuiz.get().getIsAuthor(),
                myQuiz.get().getAllowedUsers()
        );

        quizRepository.save(modifiedQuiz);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{quizId}/delete")
    public ResponseEntity<String> deleteQuiz(@PathVariable Long quizId, Principal principal) {
        String authorName = principal.getName();
        Optional<AppUser> author = jpaUserDetailsService.findByUsernameOrEmail(authorName, authorName);

        Optional<Quiz> quizToDelete = quizService.findById(quizId);
        if(!quizToDelete.isPresent()){
            throw new QuizNotFoundException();
        }

        Quiz quiz = quizToDelete.get();

        if (!quiz.getAuthor().equals(author.get())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only the author of a quiz can delete it.");
        }

        quiz.setDeleted(true);
        quizService.save(quiz);

        return ResponseEntity.ok("Quiz was deleted successfully.");
    }

    private boolean isPostDataValid(String title,
                                    String description,
                                    QuizStatus status,
                                    QuizGrading grading) {
        return(isStringValid(title) && isStringValid(description) && status != null && grading != null);
    }

    private boolean isStringValid(String s) {
        return (s != null && !s.isEmpty());
    }

    private String getStoreQuizErrorResponse(RedirectAttributes redirectAttributes, Quiz quiz, String errorMessage){
        redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
        redirectAttributes.addFlashAttribute("quiz", quiz);
        return "redirect:/quizzes/create";
    }
}



