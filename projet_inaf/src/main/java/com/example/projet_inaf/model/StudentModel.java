package com.example.projet_inaf.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.action.internal.OrphanRemovalAction;

import java.util.List;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentModel {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id_Student;

   @Column(name = "NAME")
   private String name;

    @Column(name = "LASTNAME")
    private String lastname;

    @Column(name = "NUMBER")
    private int number;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<TrainingModel> training;

    @ManyToMany(mappedBy = "students",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CourseModel> courses;


}
