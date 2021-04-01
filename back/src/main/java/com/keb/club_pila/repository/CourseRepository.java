package com.keb.club_pila.repository;

import com.keb.club_pila.model.entity.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

   Optional<Course> findById(Long id);
   @Query(value = "SELECT * FROM course WHERE id = :iD", nativeQuery = true)
   Course findById2(@Param("iD")Long iD);
   /*List<Course> findAllCourses();*/
}
