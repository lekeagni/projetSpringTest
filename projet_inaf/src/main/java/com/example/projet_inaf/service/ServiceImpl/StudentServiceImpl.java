package com.example.projet_inaf.service.ServiceImpl;

import com.example.projet_inaf.exception.StudentAlreadyExistException;
import com.example.projet_inaf.exception.StudentNotFoundException;
import com.example.projet_inaf.model.StudentModel;
import com.example.projet_inaf.repository.StudentRepository;
import com.example.projet_inaf.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    @Autowired
    public  StudentRepository studentRepository;

    @Override
    public StudentModel createStudent(StudentModel studentModel) {
        Optional<StudentModel> found = this.studentRepository.findByName(studentModel.getName());
            if(found.isPresent()){
                log.error("student with name {} already exist",studentModel.getName());
                throw new StudentAlreadyExistException(studentModel.getName());

            }
        log.info("add student");
        return this.studentRepository.save(studentModel);
    }

    @Override
    public List<StudentModel> getAllStudent() {

        return this.studentRepository.findAll();
    }

    @Override
    public StudentModel updateStudent(int id_Student, StudentModel studentModel) {
        Optional<StudentModel> foundStudent= this.studentRepository.findById(id_Student);
        if(id_Student == studentModel.getId_Student() && foundStudent.isPresent()){
            StudentModel exist = foundStudent.get();

            exist.setName(studentModel.getName());
            exist.setLastname(studentModel.getLastname());
            exist.setNumber(studentModel.getNumber());
            exist.setEmail(studentModel.getEmail());
            exist.setPassword(studentModel.getPassword());

            log.info("update student with {}",id_Student);
            return this.studentRepository.save(exist);
        }else {
            log.error("not update student with id {}", id_Student);
            throw new StudentNotFoundException(id_Student);
        }
    }

    @Override
    public Boolean deleteStudent(int id_Student) {

        Optional<StudentModel> foundTraining = this.studentRepository.findById(id_Student);

        if(foundTraining.isPresent()){
            StudentModel t = foundTraining.get();
            log.info("successfully to delete student with ID: {}", id_Student);
            this.studentRepository.delete(t);
            return true;
        }
        log.error("could not delete student. student with ID: {} not found ",id_Student);
        throw new StudentNotFoundException(id_Student);

    }

}
