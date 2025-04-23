package com.example.projet_inaf.exception;

public class TrainingAlreadyExistException extends CustomException {
    public TrainingAlreadyExistException(String name) {

      super("training already exist wiht" +name);
    }
}
