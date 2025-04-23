package com.example.projet_inaf.service;

import com.example.projet_inaf.model.TrainingModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TrainingService {

    public TrainingModel create(TrainingModel trainingModel);

    public List<TrainingModel> getAll();

    public TrainingModel update(int id, TrainingModel trainingModel);

    public Boolean delete(int id);

}
