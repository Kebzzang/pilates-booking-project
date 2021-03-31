package com.keb.club_pila.controller;

import com.keb.club_pila.dto.CourseConverter;
import com.keb.club_pila.dto.course.CourseDto;
import com.keb.club_pila.dto.course.CourseUpdateRequestDto;
import com.keb.club_pila.model.entity.course.Course;
import com.keb.club_pila.model.response.BasicResponse;
import com.keb.club_pila.model.response.CommonResponse;
import com.keb.club_pila.model.response.ErrorResponse;
import com.keb.club_pila.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

   /* @GetMapping("/api/v1/course/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
//        Course entity = courseService.findById(id);
//        return new ResponseEntity<CourseDto>(converter.entityToDto(entity), HttpStatus.OK);

    }*/
    @GetMapping("/api/v1/course/{id}")
    public ResponseEntity<? extends BasicResponse> findById(@PathVariable("id") Long id) {
       Optional<Course> course= Optional.ofNullable(courseService.findById(id));
        System.out.println();
        if(!course.isPresent()){
            System.out.println("problem here");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("잘못된 수업 아이디 조회: "+id));
        }
        return ResponseEntity.ok().body(new CommonResponse<CourseDto>(converter.entityToDto(course.get())));
    }

    @GetMapping("/api/v1/course")
    public ResponseEntity<List<CourseDto>> findAll() {
        System.out.println("findAll controller");
        return new ResponseEntity<List<CourseDto>>(converter.entityToDto(courseService.courseList()), HttpStatus.OK);
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