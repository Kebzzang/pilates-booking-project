package com.keb.club_pila.repository;

import com.keb.club_pila.model.entity.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public interface CourseRepository extends JpaRepository<Course, Long> {

   //Optional<Course> findById(Long id);

    List<Course> findAllByCourseDateTimeBetween(LocalDateTime start, LocalDateTime end);

}
