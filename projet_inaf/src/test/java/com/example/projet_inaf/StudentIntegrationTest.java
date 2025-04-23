package com.example.projet_inaf;

import com.example.projet_inaf.model.StudentModel;
import com.example.projet_inaf.repository.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void testCreateStudent() throws Exception {
        StudentModel student = new StudentModel();
        student.setName("KENFACK");
        student.setLastname("Jorelle");
        student.setEmail("jk23@gmail.com");
        student.setNumber(679002772);
        student.setPassword("qwertyui");

        mockMvc.perform(post("/student/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("KENFACK"));
    }

    @Test
    public void testGetAllStudent() throws Exception {
        mockMvc.perform(get("/student")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateStudent() throws Exception {
        StudentModel student = new StudentModel();
        student.setId_Student(7);
        student.setName("KENFACK MAFFO");
        student.setLastname("Jorelle");
        student.setEmail("jk23@gmail.com");
        student.setNumber(679002772);
        student.setPassword("qwertyui");

        mockMvc.perform(put("/student/update/7")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.name").value("KENFACK MAFFO"));
    }

    @Test
    public void testDeleteStudent() throws Exception {
        mockMvc.perform(delete("/student/delete/7"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
