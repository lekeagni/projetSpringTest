package com.example.projet_inaf.service.ServiceImpl;

import com.example.projet_inaf.exception.TrainingAlreadyExistException;
import com.example.projet_inaf.exception.TrainingNotFoundException;
import com.example.projet_inaf.model.TrainingModel;
import com.example.projet_inaf.repository.TrainingRepository;
import com.example.projet_inaf.service.TrainingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TraningServiceImpl implements TrainingService {

    @Autowired
    private TrainingRepository trainingRepository;

    @Override
    public TrainingModel create(TrainingModel trainingModel) {
        Optional<TrainingModel> found = this.trainingRepository.findByName(trainingModel.getName());
        if(found.isPresent()){
            log.error(" faild to add training ");
            throw  new TrainingAlreadyExistException(trainingModel.getName());
        }
        log.info("add training successfull");
        return this.trainingRepository.save(trainingModel);
    }

    @Override
    public List<TrainingModel> getAll() {
        return this.trainingRepository.findAll();
    }

    @Override
    public TrainingModel update(int id, TrainingModel trainingModel) {
        Optional<TrainingModel> foundTraining = this.trainingRepository.findById(id);
        if(id == trainingModel.getId() && foundTraining.isPresent()){
            log.info("updated successful with {} ", id);
            return this.trainingRepository.save(trainingModel);
        }else {
            log.error("error to update training with {}", id);
            throw new TrainingNotFoundException(id);
        }
    }

    @Override
    public Boolean delete(int id) {
        Optional<TrainingModel> found = this.trainingRepository.findById(id);
        if (found.isPresent()){
            TrainingModel u = found.get();
            log.info("delete training successful with {}",id);
            this.trainingRepository.delete(u);

            return true;
        }
        throw new TrainingNotFoundException(id);
    }
}
