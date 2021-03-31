package com.keb.club_pila.controller;

import com.keb.club_pila.dto.CourseConverter;
import com.keb.club_pila.dto.course.CourseDto;
import com.keb.club_pila.dto.course.CourseUpdateRequestDto;
import com.keb.club_pila.entity.course.Course;
import com.keb.club_pila.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CourseApiController {
    private final CourseService courseService;
    private final CourseConverter converter;

    //private final PostsResponseDto postsResponseDto;
    @PostMapping("/api/v1/course")
    public Long save(@RequestBody CourseDto courseDto) {
       return courseService.save(converter.dtoToEntity(courseDto));

    }

    @GetMapping("/api/v1/course/{id}")
    public CourseDto findById(@PathVariable(value = "id") Long id) {
        Course entity = courseService.findById(id);
        return converter.entityToDto(entity);
    }

    @GetMapping("/api/v1/course")

    public List<CourseDto> findAll() {
        System.out.println("findAll controller");
        return converter.entityToDto(courseService.courseList());
    }

    /*@GetMapping("/api/v1/course/teacher/{teacher}")
    public List<CourseDto> findByTeacher(@PathVariable(value = "teacher") String teacher) {
        System.out.println("findByTeacher");
        return converter.entityToDto(courseService.findByTeacher(teacher));
    }*/

    @PutMapping("/api/v1/course/{id}")
    public Long update(@PathVariable Long id, @RequestBody CourseUpdateRequestDto requestDto) {
        return courseService.update(id, requestDto);
    }

}