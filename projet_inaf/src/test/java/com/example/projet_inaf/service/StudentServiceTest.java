package com.example.projet_inaf.service;

import com.example.projet_inaf.exception.StudentAlreadyExistException;
import com.example.projet_inaf.exception.StudentNotFoundException;
import com.example.projet_inaf.model.StudentModel;
import com.example.projet_inaf.repository.StudentRepository;
import com.example.projet_inaf.service.ServiceImpl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest
//@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private StudentServiceImpl studentService;

    private StudentModel studentModel;

    @BeforeEach
    void setUp() {
        studentModel  = new StudentModel(7,"TENTO","Doe",677889900,"john.doe@example.com","johncarter",null,null);
    }

    @Test
    @DisplayName("create a student successfull")
    void ShouldCreateStudent() {
        when(studentRepository.findByName(studentModel.getName())).thenReturn(Optional.empty());
        when(studentRepository.save(any(StudentModel.class))).thenReturn(studentModel);

        StudentModel savestdent = studentService.createStudent(studentModel);

        assertThat(savestdent).isNotNull();
        assertThat(savestdent.getId_Student()).isEqualTo(7);
        verify(studentRepository,times(1)).save(any(StudentModel.class));
    }

    @Test
    @DisplayName("lever une exception lorsqu'un etudiant existe dejà")
    void ShouldThrowExceptionWhenStdentAlreadyExist(){
        when(studentRepository.findByName(studentModel.getName())).thenReturn(Optional.of(studentModel));

        assertThatThrownBy(()->studentService.createStudent(studentModel))
                .isInstanceOf(StudentAlreadyExistException.class);
    }

    @Test
    @DisplayName("recuperer tous les etudiants de la base des donnees")
    void ShouldReturnAllStudents() {
        List<StudentModel> students = Arrays.asList(studentModel, new StudentModel(8, "JANOU", "Smith", 678900122, "janesmith@example.com", "password", null, null));

        when(studentRepository.findAll()).thenReturn(students);
        List<StudentModel> fetchedStudents = studentService.getAllStudent();

        assertThat(fetchedStudents).isNotNull().hasSize(2);
        verify(studentRepository,times(1)).findAll();
    }

    @Test
    @DisplayName("effectuer des mis a jour sur un etudiant")
    void ShouldUpdatedStudent(){
        when(studentRepository.findById(anyInt())).thenReturn(Optional.of(studentModel));
        when(studentRepository.save(any(StudentModel.class))).thenReturn(studentModel);

        StudentModel updateStudent  = studentService.updateStudent(7,studentModel);

        assertThat(updateStudent).isNotNull();
        verify(studentRepository,times(1)).save(any(StudentModel.class));
    }

    @Test
    @DisplayName("lever une exception lorsque l'etudiant a mettre a jour n'existe pas")
    void ShouldThrowExceptionWhenUpdateStudentNotFound(){
        when(studentRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(()->studentService.updateStudent(1,studentModel))
                .isInstanceOf(StudentNotFoundException.class);
    }
    @Test
    @DisplayName("test de suppresion d'un etudiant")
    void ShouldDeleteStudent(){
        when(studentRepository.findById(anyInt())).thenReturn(Optional.of(studentModel));
        doNothing().when(studentRepository).delete(any(StudentModel.class));

        boolean isDeleted = studentService.deleteStudent(7);

        assertThat(isDeleted).isTrue();
        verify(studentRepository, times(1)).delete(any(StudentModel.class));
    }

    @Test
    @DisplayName("lever une exception lorsque l'etudiant a supprimer n'existe pas")
    void ShouldThrowExceptionWhenDeleteStudentNotFound(){
        when(studentRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(()->studentService.deleteStudent(1)).isInstanceOf(StudentNotFoundException.class);

    }

}
