package com.ateam.backend.controllers;

import com.ateam.backend.models.quiz.*;
import com.ateam.backend.repositories.AnswerRepository;
import com.ateam.backend.services.AnswerService;
import com.ateam.backend.services.QuestionService;
import com.ateam.backend.services.QuizService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AnswerControllerUnitTest {

    @InjectMocks
    AnswerController answerController;

    @Mock
    AnswerService answerService;

    @Mock
    QuestionService questionService;

    @Mock
    QuizService quizService;
    @Mock
    QuizController quizController;

    @Mock
    AnswerRepository answerRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void delete_answer() {
        Answer answer = new Answer();
        answer.setDeleted(false);
        when(answerService.findById(any())).thenReturn(answer);
        ResponseEntity<String> responseEntity = answerController.deleteAnswer(1L);
        assertEquals("Answer deleted!", responseEntity.getBody());
        assertEquals(200, responseEntity.getStatusCodeValue());
        verify(answerService, times(1)).findById(any());
        verify(answerService, times(1)).save(any());
    }

    @Test
    public void get_undeleted_answers() {
        List<Answer> expectedAnswers = new ArrayList<>();
        expectedAnswers.add(new Answer());
        when(answerService.findByDeletedFalse()).thenReturn(expectedAnswers);
        List<Answer> actualAnswers = answerController.getUndeletedAnswers();
        assertEquals(expectedAnswers, actualAnswers);
        verify(answerService, times(1)).findByDeletedFalse();
    }
    @Test
    public void create_answer_single_choice() {
        Long quizId = 1L;
        Long questionId = 1L;
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer(null, null, "Answer 1", "Description 1", true));
        answers.add(new Answer(null, null, "Answer 2", "Description 2", false));
        answers.add(new Answer(null, null, "Answer 3", "Description 3", false));

        Question question = new Question();
        question.setId(questionId);
        question.setType(QuestionType.SINGLE_CHOICE);
        Mockito.when(questionService.findById(questionId)).thenReturn(Optional.of(question));

        ResponseEntity<String> response = answerController.createAnswer(quizId, questionId, answers);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    public void create_answer_multiple_choice() {
        Long quizId = 1L;
        Long questionId = 5L;
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer(null, null, "Answer 1", "Description 1", true));
        answers.add(new Answer(null, null, "Answer 2", "Description 2", false));
        answers.add(new Answer(null, null, "Answer 3", "Description 3", true));

        Question question = new Question();
        question.setId(questionId);
        question.setType(QuestionType.MULTIPLE_CHOICE);
        Mockito.when(questionService.findById(questionId)).thenReturn(Optional.of(question));

        ResponseEntity<String> response = answerController.createAnswer(quizId, questionId, answers);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void create_answer_fill_out() {
        // Set up test data
        Long quizId = 1L;
        Long questionId = 3L;
        Answer answer = new Answer(null, null, "Paris", "Capital of France", false); // answer should initially be incorrect
        List<Answer> answers = Collections.singletonList(answer);

        Question question = new Question();
        question.setId(questionId);
        question.setType(QuestionType.FILL_OUT);
        question.setDescription("What is the capital of France?");
        Mockito.when(questionService.findById(questionId)).thenReturn(Optional.of(question));

        AnswerController answerController = new AnswerController(answerService, questionService, quizService, answerRepository);

        ResponseEntity<String> response = answerController.createAnswer(quizId, questionId, answers);
        assertEquals("Paris", answer.getTitle());
        assertEquals("Capital of France", answer.getDescription());
    }

    @Test
    public void create_answer_pair_assign() {
        Long quizId = 1L;
        Long questionId = 2L;
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer(null, null, "A;1,B;2,C;3", "A;1,B;2,C;3", true));

        Question question = new Question();
        question.setId(questionId);
        question.setType(QuestionType.PAIR_ASSIGN);
        question.setDescription("A;1,B;2,C;3");
        Mockito.when(questionService.findById(questionId)).thenReturn(Optional.of(question));

        ResponseEntity<String> response = answerController.createAnswer(quizId, questionId, answers);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void modify_answer() {
        Answer answer = new Answer(null, null, "Answer Title", "Answer Description", true);
        Quiz quiz = new Quiz(null,"title","description",QuizStatus.ENABLED,QuizVisibility.PUBLIC,QuizGrading.POINT_PER_ANSWER,null,true,List
                .of());
        Question question = new Question();

        AnswerToModifyDto answerToModifyDto = new AnswerToModifyDto("Modified Title", "Modified Description", false);

        when(answerService.findById(1L)).thenReturn(answer);
        when(questionService.findById(1L)).thenReturn(Optional.of(question));
        when(quizService.findById(1L)).thenReturn(Optional.of(quiz));
        ArgumentCaptor<Answer> answerCaptor = ArgumentCaptor.forClass(Answer.class);

        ResponseEntity responseEntity = answerController.modify(1L, 1L, 1L, answerToModifyDto);
        verify(answerRepository).save(answerCaptor.capture());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Answer result = answerCaptor.getValue();

        assertEquals("Modified Title", result.getTitle());
        assertEquals("Modified Description", result.getDescription());
        assertFalse(result.isCorrect());
    }
}