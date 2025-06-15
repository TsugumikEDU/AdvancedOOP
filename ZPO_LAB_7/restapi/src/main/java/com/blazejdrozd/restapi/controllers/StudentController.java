package com.blazejdrozd.restapi.controllers;

import com.blazejdrozd.restapi.models.Student;
import com.blazejdrozd.restapi.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{albumNumber}")
    public ResponseEntity<Student> getStudentByAlbumNumber(@PathVariable Integer albumNumber) {
        return ResponseEntity.ok(studentService.getByAlbumNumber(albumNumber));
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student savedStudent = studentService.addStudent(student);
        return ResponseEntity
                .created(URI.create("/api/students/" + savedStudent.getAlbumNumber()))
                .body(savedStudent);
    }

    @PutMapping("/{albumNumber}")
    public ResponseEntity<Student> updateStudent(@PathVariable Integer albumNumber, @RequestBody Student updatedStudent) {
        return ResponseEntity.ok(studentService.updateStudent(albumNumber, updatedStudent));
    }

    @DeleteMapping("/{albumNumber}")
    public ResponseEntity<Student> deleteStudentByAlbumNumber(@PathVariable Integer albumNumber) {
        return ResponseEntity.ok(studentService.deleteStudentByAlbumNumber(albumNumber));
    }
}
