package com.blazejdrozd.restapi.repos;

import com.blazejdrozd.restapi.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IStudentRepo extends JpaRepository<Student, Integer> {
    Optional<Student> findByAlbumNumber(Integer albumNumber);

    boolean existsByAlbumNumber(Integer albumNumber);

    void deleteByAlbumNumber(Integer albumNumber);

    List<Student> getByAlbumNumber(Integer albumNumber);
}
