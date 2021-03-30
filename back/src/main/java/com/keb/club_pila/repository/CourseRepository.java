package com.keb.club_pila.repository;

import com.keb.club_pila.domain.courses.Course;
import com.keb.club_pila.dto.course.CourseResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
   List<Course> findByTeacher(String teacher);
}
