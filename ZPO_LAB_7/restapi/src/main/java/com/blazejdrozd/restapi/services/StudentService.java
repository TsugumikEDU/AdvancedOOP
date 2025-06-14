package com.blazejdrozd.restapi.services;

import com.blazejdrozd.restapi.exceptions.StudentRepoAlreadyExistsException;
import com.blazejdrozd.restapi.exceptions.StudentRepoNotFoundException;
import com.blazejdrozd.restapi.exceptions.StudentRepoWrongDataProvided;
import com.blazejdrozd.restapi.models.Student;
import com.blazejdrozd.restapi.repos.IStudentRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final IStudentRepo studentRepo;

    public StudentService(IStudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    public Student addStudent(Student student) {
        if (student.getId() != null) {
            throw new StudentRepoWrongDataProvided("Student ID is generated automatically");
        }

        if (student.getAlbumNumber() == null) {
            throw new StudentRepoWrongDataProvided("Album Number is empty");
        }

        if (studentRepo.existsByAlbumNumber(student.getAlbumNumber())) {
            throw new StudentRepoAlreadyExistsException(student.getAlbumNumber());
        }

        return studentRepo.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    public Optional<Student> getByAlbumNumber(Integer albumNumber) {
        return studentRepo.findByAlbumNumber(albumNumber);
    }

    public Student updateStudent(Integer albumNumber, Student updatedStudent) {
        Student student = getByAlbumNumber(albumNumber)
                .orElseThrow(() -> new StudentRepoNotFoundException(albumNumber));

        if (updatedStudent.getAlbumNumber() == null) {
            throw new StudentRepoWrongDataProvided("Album Number is empty");
        }

        if (!student.getAlbumNumber().equals(updatedStudent.getAlbumNumber())
                && studentRepo.existsByAlbumNumber(updatedStudent.getAlbumNumber())) {
            throw new StudentRepoWrongDataProvided("Album number is already taken");
        }

        student.setFirstName(updatedStudent.getFirstName());
        student.setLastName(updatedStudent.getLastName());
        student.setAlbumNumber(updatedStudent.getAlbumNumber());

        return studentRepo.save(student);
    }

    public Student deleteStudentByAlbumNumber(Integer albumNumber) {
        Optional<Student> student = getByAlbumNumber(albumNumber);

        if (student.isEmpty()) {
            throw new StudentRepoNotFoundException(albumNumber);
        }

        studentRepo.deleteByAlbumNumber(albumNumber);
        return student.get();
    }
}
