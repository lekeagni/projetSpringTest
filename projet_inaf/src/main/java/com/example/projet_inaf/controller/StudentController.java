package com.example.projet_inaf.controller;
import com.example.projet_inaf.model.StudentModel;
import com.example.projet_inaf.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@Tag(name = "APIs RestFull pour les etudiants", description = "differents endpoints d'APIs RestFull pour gere les etudiants")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Operation(summary = "create student", description = "ajouter un nouvel étudiant en base des données")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ajout d'un utilisateur réeussie"),
            @ApiResponse(responseCode = "404", description = "Etudiant introuvable"),
            @ApiResponse(responseCode = "505", description = "Erreur du serveur")

    })
    @PostMapping("/save")
    public ResponseEntity<StudentModel> createStudent(@RequestBody StudentModel studentModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.studentService.createStudent(studentModel));
    }

    @Operation(summary = "recuperer les etudiants", description = "recuperer tous les etudiants de la base des donnees")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "recuperation reussie"),
            @ApiResponse(responseCode = "404",description = "Aucun etudiant trouvé"),
            @ApiResponse(responseCode = "505", description = "Erreur du serveur")
    })
    @GetMapping()
    public ResponseEntity<List<StudentModel>> getAllStudent(){
        return ResponseEntity.status(HttpStatus.OK).body(this.studentService.getAllStudent());
    }

    @Operation(summary = "mis a jour d'un etudiant", description = "recuperation de l'etudiant par son identifiant, mis a jour des champs et enregistrement dans la base des donnees")
    @PutMapping("/update/{id}")
    public ResponseEntity<StudentModel> updateStudent(@PathVariable int id,@RequestBody StudentModel studentModel){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(this.studentService.updateStudent(id, studentModel));
    }

    @Operation(summary = "suppression d'un etudiant", description = "")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable int id){
        boolean isDeleted= this.studentService.deleteStudent(id);
        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }



}
