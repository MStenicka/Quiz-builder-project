package com.ateam.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(QuizNotFoundException.class)
    public ResponseEntity<Object> handleExceptions(QuizNotFoundException exception, WebRequest webRequest){
        ExceptionResponseDto response = new ExceptionResponseDto();
        response.setDateTime(LocalDateTime.now());
        response.setMessage("Quiz not found");
        ResponseEntity<Object> entity = new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        return entity;
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<Object> handleExceptions(QuestionNotFoundException exception, WebRequest webRequest){
        ExceptionResponseDto response = new ExceptionResponseDto();
        response.setDateTime(LocalDateTime.now());
        response.setMessage("Question not found");
        ResponseEntity<Object> entity = new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        return entity;
    }

    @ExceptionHandler(AnswerNotFoundException.class)
    public ResponseEntity<Object> handleExceptions(AnswerNotFoundException exception, WebRequest webRequest){
        ExceptionResponseDto response = new ExceptionResponseDto();
        response.setDateTime(LocalDateTime.now());
        response.setMessage("Answer not found");
        ResponseEntity<Object> entity = new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        return entity;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleExceptions(UserNotFoundException exception, WebRequest webRequest){
        ExceptionResponseDto response = new ExceptionResponseDto();
        response.setDateTime(LocalDateTime.now());
        response.setMessage("User not found");
        ResponseEntity<Object> entity = new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        return entity;
    }

    @ExceptionHandler(ResultsNotFoundException.class)
    public ResponseEntity<Object> handleExceptions(ResultsNotFoundException exception, WebRequest webRequest){
        ExceptionResponseDto response = new ExceptionResponseDto();
        response.setDateTime(LocalDateTime.now());
        response.setMessage("Results not found");
        ResponseEntity<Object> entity = new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        return entity;
    }
}

