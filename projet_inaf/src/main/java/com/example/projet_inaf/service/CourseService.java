package com.example.projet_inaf.service;

import com.example.projet_inaf.model.CourseModel;
import org.springframework.stereotype.Service;

import javax.swing.text.StyledEditorKit;
import java.util.List;

@Service
public interface CourseService {

    public CourseModel create(CourseModel courseModel, int id_Student);

//    public CourseModel addStudentToCourse( int course_id, int id_Student);

    public List<CourseModel>getAll();

    public CourseModel update(int course_id, CourseModel courseModel);

    public Boolean delete(int course_id);
}
