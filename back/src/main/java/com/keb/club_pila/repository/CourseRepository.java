package com.keb.club_pila.repository;

import com.keb.club_pila.model.entity.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CourseRepository extends JpaRepository<Course, Long> {

   //Optional<Course> findById(Long id);
}
