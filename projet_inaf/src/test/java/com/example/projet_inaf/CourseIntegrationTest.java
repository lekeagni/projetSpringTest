package com.example.projet_inaf;

import com.example.projet_inaf.model.CourseModel;
import com.example.projet_inaf.model.StudentModel;
import com.example.projet_inaf.repository.CourseRepository;
import com.example.projet_inaf.repository.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class CourseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private  ObjectMapper objectMapper;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public  void CreateCourseTest()throws Exception{
        CourseModel courseModel = new CourseModel();
        courseModel.setTitle("Physique quantique");

        mockMvc.perform(post("/course/save")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(courseModel)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Physique quantique"));
    }

    @Test
    public  void GetAllCoursesTest() throws  Exception{
        mockMvc.perform(get("/course")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void UpdateCourseTest() throws Exception{
        CourseModel courseModel = new CourseModel();
        courseModel.setTitle("Geophysique");
        courseModel = courseRepository.save(courseModel);

        courseModel.setTitle("Geophysique de l'environnement");

        mockMvc.perform(put("/course/update/" + courseModel.getCourse_id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(courseModel)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.title").value("Geophysique de l'environnement"));
    }

    @Test
    public void DeleteCourseTest()throws Exception{
        CourseModel courseModel = new CourseModel();
        courseModel.setTitle("Anglais");
        courseModel = courseRepository.save(courseModel);

        mockMvc.perform(delete("/course/delete/"+courseModel.getCourse_id())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(content().string("true"));

    }

    @Test
    public void AddStudentCourseTest() throws Exception{
        StudentModel studentModel  = new StudentModel();
        studentModel.setName("FEUSIO LIFAC");
        studentModel.setLastname("Ebenezer njoh");
        studentModel.setNumber(688900099);
        studentModel.setEmail("njohm@gmail.com");
        studentModel.setPassword("azertyui");
        studentModel = studentRepository.save(studentModel);

        CourseModel  course = new CourseModel();
        course.setTitle("Electronique");
        course = courseRepository.save(course);

        mockMvc.perform(put("/course/update/"+course.getCourse_id()+ "/" +studentModel.getId_Student())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

 mockMvc.perform(put("/course/update/"+course.getCourse_id()+ "/" +studentModel.getId_Student())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());

    }
}
