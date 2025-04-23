package com.example.projet_inaf.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(Integer course_id) {

        super("course not found with" + course_id);
    }
}
