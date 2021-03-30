package com.keb.club_pila.controller;

import com.keb.club_pila.domain.courses.Course;
import com.keb.club_pila.dto.course.CourseResponseDto;
import com.keb.club_pila.dto.course.CourseSaveRequestDto;
import com.keb.club_pila.dto.course.CourseUpdateRequestDto;
import com.keb.club_pila.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CourseApiController {
    private final CourseService courseService;
    //private final PostsResponseDto postsResponseDto;
    @PostMapping("/api/v1/course")
    public Long save(@RequestBody CourseSaveRequestDto requestDto){
        return courseService.save(requestDto);
    }
    @GetMapping("/api/v1/course/{id}")
    public CourseResponseDto findById (@PathVariable Long id){
        return courseService.findById(id);
    }
    @GetMapping("/api/v1/course/teacher/{teacher}")
    public void findByTeacher(@PathVariable String teacher, Model model){
        List<CourseResponseDto> courseResponseDtoList=courseService.findByTeacher(teacher);
      for(CourseResponseDto dto: courseResponseDtoList){
          System.out.println(dto.getTitle());
      }
        }
    @PutMapping("/api/v1/course/{id}")
    public Long update(@PathVariable Long id, @RequestBody CourseUpdateRequestDto requestDto){
        return courseService.update(id, requestDto);
    }

}