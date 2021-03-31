package com.keb.club_pila.service;

import com.keb.club_pila.model.entity.course.Course;
import com.keb.club_pila.dto.course.CourseUpdateRequestDto;
import com.keb.club_pila.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    @Transactional
    public Long save(Course course) {
        return courseRepository.save(course).getId();
    }

    @Transactional
    public Long update(Long id, CourseUpdateRequestDto requestDto) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 수업이 없습니다. id=" + id));
        course.update(requestDto.getTitle(), requestDto.getContent());

        return id;

    }

    public List<Course> courseList() {
        return courseRepository.findAll();
    }

    public Course findById(Long id) {
        System.out.println("No prob here");
        return courseRepository.findById(id).orElse(null);

    }

    public List<Course> findByTeacher(String teacher) {
        List<Course> courses = courseRepository.findByTeacher(teacher);
        return courseRepository.findByTeacher(teacher);
    }
}
