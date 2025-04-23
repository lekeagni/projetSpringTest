package com.example.projet_inaf.controller;

import com.example.projet_inaf.exception.StudentAlreadyExistException;
import com.example.projet_inaf.exception.StudentNotFoundException;
import com.example.projet_inaf.model.StudentModel;
import com.example.projet_inaf.service.ServiceImpl.StudentServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
@ExtendWith(SpringExtension.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentServiceImpl studentService;

    @Autowired
    private ObjectMapper objectMapper;

    private StudentModel student;

    @BeforeEach
    void setUp() {
        student = new StudentModel(7, "TENTO", "Doe", 677889900, "john.doe@example.com", "johncarter", null, null);
    }

    @Test
    @DisplayName("Créer un étudiant avec succès")
    void shouldCreateStudent() throws Exception {
        when(studentService.createStudent(any(StudentModel.class))).thenReturn(student);

        mockMvc.perform(post("/student/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_Student").value(7))
                .andExpect(jsonPath("$.name").value("TENTO"));
    }

    @Test
    @DisplayName("Retourne une erreur si l'étudiant existe déjà")
    void shouldThrowExceptionWhenStudentAlreadyExists() throws Exception {
        when(studentService.createStudent(any(StudentModel.class))).thenThrow(StudentAlreadyExistException.class);

        mockMvc.perform(post("/student/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Récupérer tous les étudiants")
    void shouldReturnAllStudents() throws Exception {
        List<StudentModel> students = Arrays.asList(student, new StudentModel(8, "JANOU", "Smith", 678900122, "janesmith@example.com", "password", null, null));
        when(studentService.getAllStudent()).thenReturn(students);

        mockMvc.perform(get("/student"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    @DisplayName("Mettre à jour un étudiant")
    void shouldUpdateStudent() throws Exception {
        when(studentService.updateStudent(anyInt(), any(StudentModel.class))).thenReturn(student);

        mockMvc.perform(put("/student/update/7")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id_Student").value(7))
                .andExpect(jsonPath("$.name").value("TENTO"));
    }

    @Test
    @DisplayName("Retourne une erreur si l'étudiant à mettre à jour n'existe pas")
    void shouldThrowExceptionWhenUpdatingNonExistingStudent() throws Exception {
        when(studentService.updateStudent(anyInt(), any(StudentModel.class))).thenThrow(StudentNotFoundException.class);

        mockMvc.perform(put("/student/update/7")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Supprimer un étudiant")
    void shouldDeleteStudent() throws Exception {
        when(studentService.deleteStudent(anyInt())).thenReturn(true);

        mockMvc.perform(delete("/student/delete/7"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Retourne une erreur si l'étudiant à supprimer n'existe pas")
    void shouldThrowExceptionWhenDeletingNonExistingStudent() throws Exception {
        when(studentService.deleteStudent(anyInt())).thenThrow(StudentNotFoundException.class);

        mockMvc.perform(delete("/student/delete/7"))
                .andExpect(status().isNotFound());
    }
}