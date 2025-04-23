package com.example.projet_inaf.exception;

public class TrainingNotFoundException extends CustomException {
    public TrainingNotFoundException(Integer id) {

      super("training not found wiht " +id);
    }
}
