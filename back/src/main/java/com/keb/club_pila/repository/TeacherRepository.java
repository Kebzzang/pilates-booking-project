package com.keb.club_pila.repository;

import com.keb.club_pila.model.entity.course.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
