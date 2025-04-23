package com.example.projet_inaf.exception;

public class StudentNotFoundException extends CustomException {

    public StudentNotFoundException(Integer id_Student) {
        super("student not found exception" + id_Student);
    }
}
