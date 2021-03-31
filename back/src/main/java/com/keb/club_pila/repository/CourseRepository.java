package com.keb.club_pila.repository;

import com.keb.club_pila.model.entity.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
   List<Course> findByTeacher(String teacher);
   @Nullable
   Optional<Course> findById(Long id);

}
