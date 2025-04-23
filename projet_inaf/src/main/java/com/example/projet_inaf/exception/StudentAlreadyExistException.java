package com.example.projet_inaf.exception;

public class StudentAlreadyExistException extends RuntimeException {
    public StudentAlreadyExistException(String name) {

        super("L'etudiant avec ce nom existe déjà" + name);
    }
}
