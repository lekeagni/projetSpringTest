package com.example.projet_inaf.exception;

import com.example.projet_inaf.model.ErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Collections;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(StudentNotFoundException.class)
  public ResponseEntity<ErrorException> HandlerStudentNotFoundException(StudentNotFoundException exception){
    ErrorException errorException = ErrorException.builder()
            .LocalDateTime(LocalDateTime.now())
            .message(exception.getMessage())
            .HttpStatus(HttpStatus.NOT_FOUND.value())
            .build();

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorException);
  }

  @ExceptionHandler(TrainingNotFoundException.class)
  public ResponseEntity<ErrorException> HandlerTrainingNotFoundException(TrainingNotFoundException exception){
    ErrorException errorException = ErrorException.builder()
            .LocalDateTime(LocalDateTime.now())
            .message(exception.getMessage())
            .HttpStatus(HttpStatus.NOT_FOUND.value())
            .build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorException);
  }

  @ExceptionHandler(CourseNotFoundException.class)
  public ResponseEntity<ErrorException> HandlerCourseNotFoundException( CourseNotFoundException exception){
    ErrorException errorException = ErrorException.builder()
            .LocalDateTime(LocalDateTime.now())
            .message(exception.getMessage())
            .HttpStatus(HttpStatus.NOT_FOUND.value())
            .build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorException);
  }

  @ExceptionHandler(StudentAlreadyExistException.class)
  public ResponseEntity<ErrorException>HandlerStudentAlreadyExistException(StudentAlreadyExistException exception){
    ErrorException errorException = ErrorException.builder()
            .LocalDateTime(LocalDateTime.now())
            .message(exception.getMessage())
            .HttpStatus(HttpStatus.CONFLICT.value())
            .build();
    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorException);
  }

  @ExceptionHandler(TrainingAlreadyExistException.class)
  public ResponseEntity<ErrorException> HandlerTrainingAlreadyExistException(TrainingAlreadyExistException exception){
    ErrorException errorException = ErrorException.builder()
            .LocalDateTime(LocalDateTime.now())
            .message(exception.getMessage())
            .HttpStatus(HttpStatus.CONFLICT.value())
            .build();
    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorException);
  }

  @ExceptionHandler(CourseAlreadyExistException.class)
  public ResponseEntity<ErrorException> HandlerCourseAlreadyExistException(CourseAlreadyExistException exception){
    ErrorException errorException = ErrorException.builder()
            .LocalDateTime(LocalDateTime.now())
            .message(exception.getMessage())
            .HttpStatus(HttpStatus.CONFLICT.value())
            .build();
    return  ResponseEntity.status(HttpStatus.CONFLICT).body(errorException);
  }

}
