package com.example.projet_inaf;

import com.example.projet_inaf.model.StudentModel;
import com.example.projet_inaf.model.TrainingModel;
import com.example.projet_inaf.repository.StudentRepository;
import com.example.projet_inaf.repository.TrainingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@SpringBootTest
@AutoConfigureMockMvc
public class TrainingIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private StudentRepository studentRepository;

    private StudentModel student;

    @BeforeEach
    void setUp(){
        trainingRepository.deleteAll();
        studentRepository.deleteAll();

        student = new StudentModel();

        student.setName("DONFOTSO");
        student.setLastname("norbert");
        student.setNumber(120976543);
        student.setEmail("nor67@gmail.com");
        student.setPassword("azerty12");

        student = studentRepository.save(student);

    }

    @Test
    public void TestIntegrationCreateTraining() throws Exception{

        TrainingModel training = new TrainingModel();
        training.setName("graphisme et production");
        training.setDescription("conception des elements de communication visuelle");
        training.setStudent(student);

        mockMvc.perform(post("/training/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(training)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("graphisme et production"))
                .andExpect(jsonPath("$.description").value("conception des elements de communication visuelle"));


    }

    @Test
    public void TestIntegrationGetAllTraining() throws Exception{
        TrainingModel training1  = new TrainingModel();
        training1.setName("secretariat");
        training1.setDescription("des1");
        training1.setStudent(student);
        trainingRepository.save(training1);

        TrainingModel training2 = new TrainingModel();
        training2.setName("maintenance");
        training2.setDescription("des2");
        training2.setStudent(student);
        trainingRepository.save(training2);

        mockMvc.perform(get("/training")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("secretariat"))
                .andExpect(jsonPath("$[1].name").value("maintenance"));

    }
    @Test
    public void TestIntegrationUpdateTraining() throws Exception {
        TrainingModel training = new TrainingModel();
        training.setName("developpement d'application");
        training.setDescription("conception des applications web");
        training .setStudent(student);
        training = trainingRepository.save(training);

        training.setName("developpement mobile et desktop");
        training.setDescription("conception des applications mobile");

        mockMvc.perform(put("/training/update/ "+training.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(training)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.name").value("developpement mobile et desktop"))
                .andExpect(jsonPath("$.description").value("conception des applications mobile"));

    }

    @Test
    public void TestIntegrationDeleteTraining() throws  Exception{

        TrainingModel trainingModel = new TrainingModel();
        trainingModel.setName("comptabilité");
        trainingModel.setDescription("analyse des donnees");
        trainingModel.setStudent(student);
        trainingModel =  trainingRepository.save(trainingModel);

        mockMvc.perform(delete("/training/delete/" + trainingModel.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
