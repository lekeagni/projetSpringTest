package com.example.projet_inaf.controller;

import com.example.projet_inaf.model.TrainingModel;
import com.example.projet_inaf.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/training")
public class TrainingController {

    @Autowired
    private TrainingService trainingService;

    @PostMapping("/save")
    public ResponseEntity<TrainingModel> create(@RequestBody TrainingModel trainingModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.trainingService.create(trainingModel));
    }

    @GetMapping()
    public ResponseEntity<List<TrainingModel>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(this.trainingService.getAll());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TrainingModel> update(@PathVariable int id, @RequestBody TrainingModel trainingModel){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(this.trainingService.update(id,trainingModel));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable int id){
        boolean found = this.trainingService.delete(id);
        if(found){
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }
}
