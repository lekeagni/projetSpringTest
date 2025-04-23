package com.example.projet_inaf.controller;

import com.example.projet_inaf.model.CourseModel;
import com.example.projet_inaf.service.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/save/{id_Student}")
    public ResponseEntity<CourseModel> create(@RequestBody CourseModel courseModel, @PathVariable int id_Student ){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.courseService.create(courseModel, id_Student));

    }

    @GetMapping()
    public ResponseEntity<List<CourseModel>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(this.courseService.getAll());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CourseModel> update(@PathVariable int id, @RequestBody CourseModel courseModel){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(this.courseService.update(id,courseModel));
    }

//    @PutMapping("update/{course_id}/{id_Student}")
//    public  ResponseEntity<CourseModel> addStudentToCourse(@PathVariable int course_id,@PathVariable int id_Student){
//
//        CourseModel updated = this.courseService.addStudentToCourse(id_Student,course_id);
//        if (updated != null){
//            return ResponseEntity.status(HttpStatus.CREATED).body(updated);
//        }
//        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable int id){
        boolean isDlete = this.courseService.delete(id);
        if (isDlete){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(true);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }
}
