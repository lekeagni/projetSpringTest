package com.example.projet_inaf.service.ServiceImpl;

import com.example.projet_inaf.exception.CourseAlreadyExistException;
import com.example.projet_inaf.exception.CourseNotFoundException;
import com.example.projet_inaf.exception.StudentNotFoundException;
import com.example.projet_inaf.model.CourseModel;
import com.example.projet_inaf.model.StudentModel;
import com.example.projet_inaf.repository.CourseRepository;
import com.example.projet_inaf.repository.StudentRepository;
import com.example.projet_inaf.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public CourseModel create(CourseModel courseModel, int id_Student) {

        Optional<CourseModel> exist = this.courseRepository.findByTitle(courseModel.getTitle());
        if (exist.isPresent()){
            throw new CourseAlreadyExistException(courseModel.getTitle());
        }
        CourseModel courseModel1 =  this.courseRepository.save(courseModel);

        Optional<StudentModel> found = this.studentRepository.findById(id_Student);
        if (found.isEmpty()){
            throw new StudentNotFoundException(id_Student);
        }
        StudentModel student = found.get();
        if (!courseModel1.getStudents().contains(student)){
            courseModel1.getStudents().add(student);
            courseModel1 = this.courseRepository.save(courseModel1);
        }
        return courseModel1;
    }

//    @Override
//    public CourseModel addStudentToCourse(int course_id, int id_Student){
//        Optional<StudentModel> studentOpt = this.studentRepository.findById(id_Student);
//        Optional<CourseModel> courseOpt = this.courseRepository.findById(course_id);
//        if (studentOpt.isPresent() && courseOpt.isPresent()){
//            StudentModel student = studentOpt.get();
//            CourseModel course = courseOpt.get();
//
//            if (course.getStudents().contains(student)){
//                return null;
//            }
//
//            course.getStudents().add(student);
//            return this.courseRepository.save(course);
//        }
//        return null;
//    }

    @Override
    public List<CourseModel> getAll() {
        return this.courseRepository.findAll();
    }

    @Override
    public CourseModel update(int course_id, CourseModel courseModel) {
        Optional<CourseModel> found = this.courseRepository.findById(course_id);
        if (course_id == courseModel.getCourse_id() && found.isPresent()){
            return this.courseRepository.save(courseModel);
        }
        throw new CourseNotFoundException(course_id);
    }

    @Override
    public Boolean delete(int course_id) {
        Optional<CourseModel> found  = this.courseRepository.findById(course_id);
        if (found.isPresent()){
            CourseModel c = found.get();
            this.courseRepository.delete(c);
            return true;
        }
        return false;
    }
}
