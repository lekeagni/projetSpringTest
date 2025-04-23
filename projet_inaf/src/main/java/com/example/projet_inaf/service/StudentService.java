package com.example.projet_inaf.service;

import com.example.projet_inaf.model.StudentModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {

    public StudentModel createStudent(StudentModel studentModel);

    public List<StudentModel> getAllStudent();

    public StudentModel updateStudent(int id_Student, StudentModel studentModel);

    public Boolean deleteStudent(int id_Student);
}
