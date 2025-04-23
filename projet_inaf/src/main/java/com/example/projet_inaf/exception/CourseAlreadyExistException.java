package com.example.projet_inaf.exception;

public class CourseAlreadyExistException extends RuntimeException {
    public CourseAlreadyExistException(String title) {

        super("Course already exit with" + title);
    }
}
