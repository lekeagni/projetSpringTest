package com.example.projet_inaf.service;

import com.example.projet_inaf.exception.TrainingAlreadyExistException;
import com.example.projet_inaf.exception.TrainingNotFoundException;
import com.example.projet_inaf.model.StudentModel;
import com.example.projet_inaf.model.TrainingModel;
import com.example.projet_inaf.repository.TrainingRepository;
import com.example.projet_inaf.service.ServiceImpl.TraningServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TrainingServiceTest {

    @MockBean
    private TrainingRepository trainingRepository;

    @Autowired
    private TraningServiceImpl traningService;

    private TrainingModel trainingModel;

    @BeforeEach
    void setUp(){
        trainingModel = new TrainingModel (1,"graphisme de production","conception des supports de communication visuelle",new StudentModel());
    }

    @Test
    @DisplayName("test pour ajouter une nouvelle formation")
    void ShouldCreateTraining(){
        when(trainingRepository.findByName(trainingModel.getName())).thenReturn(Optional.empty());
        when(trainingRepository.save(any(TrainingModel.class))).thenReturn(trainingModel);

        TrainingModel save = traningService.create(trainingModel);

        assertThat(save).isNotNull();
        assertThat(save.getId()).isEqualTo(1);
        verify(trainingRepository,times(1)).save(any(TrainingModel.class));
    }

    @Test
    void ShouldTrainingAlreadyExistingException(){
        when(trainingRepository.findByName(trainingModel.getName())).thenReturn(Optional.of(trainingModel));
         assertThatThrownBy(()->traningService.create(trainingModel)).isInstanceOf(TrainingAlreadyExistException.class);
    }

    @Test
    void ShouldGetAllTrainings(){
        List<TrainingModel> trainings = Arrays.asList(trainingModel,new TrainingModel(2,"maintenance informatique","depannage des ordinateurs",null));

        when(trainingRepository.findAll()).thenReturn(trainings);
        List<TrainingModel> getall = traningService.getAll();

        assertThat(getall).isNotNull();
        verify(trainingRepository,times(1)).findAll();

    }

    @Test
    void ShouldUpdatedTraining(){
        when(trainingRepository.findById(anyInt())).thenReturn(Optional.of(trainingModel));
        when(trainingRepository.save(any(TrainingModel.class))).thenReturn(trainingModel);

        TrainingModel updated = traningService.update(1,trainingModel);

        assertThat(updated).isNotNull();
        assertThat(updated.getId()).isEqualTo(1);
        verify(trainingRepository,times(1)).save(any(TrainingModel.class));
    }

    @Test
    void ShouldTrainingNotFoundException(){
        when(trainingRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(()->traningService.update(1,trainingModel)).isInstanceOf(TrainingNotFoundException.class);
    }

    @Test
    void ShouldDeleteTraining(){
        when(trainingRepository.findById(anyInt())).thenReturn(Optional.of(trainingModel));
        doNothing().when(trainingRepository).delete(any(TrainingModel.class));

        boolean deleted = traningService.delete(1);
        assertThat(deleted).isTrue();
        verify(trainingRepository,times(1)).delete(any(TrainingModel.class));
    }

    @Test
    void ShouldTrainingDeleteNotFoundException(){
        when(trainingRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(()->traningService.delete(1)).isInstanceOf(TrainingNotFoundException.class);
    }
}
