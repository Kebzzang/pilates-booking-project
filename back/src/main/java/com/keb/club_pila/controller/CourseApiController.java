package com.keb.club_pila.controller;

import com.keb.club_pila.dto.CourseConverter;
import com.keb.club_pila.dto.course.CourseDto;
import com.keb.club_pila.model.entity.course.Course;
import com.keb.club_pila.model.response.BasicResponse;
import com.keb.club_pila.model.response.CommonResponse;
import com.keb.club_pila.model.response.ErrorResponse;
import com.keb.club_pila.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class CourseApiController {
    private final CourseService courseService;
    private final CourseConverter converter;

    @PostMapping("/api/v1/course")
    public ResponseEntity<? extends BasicResponse> save(@RequestBody CourseDto courseDto) {
        return ResponseEntity.created(URI.create("/api/v1/course/"+courseService.save(converter.dtoToEntity(courseDto)))).build();
    }


    @GetMapping("/api/v1/course/{id}")
    public ResponseEntity<? extends BasicResponse> findById(@PathVariable("id") Long id) {

        Optional<Course> course = Optional.ofNullable(courseService.findById(id));

        if (!course.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("잘못된 수업 아이디 조회: " + id));
        }
        return ResponseEntity.ok().body(new CommonResponse<CourseDto>(converter.entityToDto(course.get())));
    }

    /*@GetMapping("/api/v1/course")
    public ResponseEntity<List<CourseDto>> findAll() {
        System.out.println("findAll controller");
        return new ResponseEntity<List<CourseDto>>(converter.entityToDto(courseService.courseList()), HttpStatus.OK);
    }*/

    @GetMapping("/api/v1/course")
    public ResponseEntity<? extends BasicResponse> findAllCourses(){
        List<Course> course=courseService.findAllCourses();

        if (course.isEmpty()) {
            System.out.println("here1");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(new CommonResponse<>(converter.entityToDto(course)));
    }




/*    @PutMapping("/api/v1/course/{id}")
    public Long update(@PathVariable Long id, @RequestBody CourseUpdateRequestDto requestDto) {
        return courseService.update(id, requestDto);
    }*/

    /*@GetMapping("/api/v1/course/teacher/{teacher}")
    public List<CourseDto> findByTeacher(@PathVariable(value = "teacher") String teacher) {
        System.out.println("findByTeacher");
        return converter.entityToDto(courseService.findByTeacher(teacher));
    }*/
/* @GetMapping("/api/v1/course/{id}")
     public ResponseEntity<?> findById(@PathVariable("id") Long id) {
 //        Course entity = courseService.findById(id);
 //        return new ResponseEntity<CourseDto>(converter.entityToDto(entity), HttpStatus.OK);

     }*/
}