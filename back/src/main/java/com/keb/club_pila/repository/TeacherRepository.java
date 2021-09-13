package com.keb.club_pila.repository;

import com.keb.club_pila.model.entity.course.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    //Teacher findById2(Long id);
    List<Teacher> findByWorkingIsTrue();
}
