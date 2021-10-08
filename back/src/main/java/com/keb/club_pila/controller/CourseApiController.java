package com.keb.club_pila.controller;

import com.keb.club_pila.dto.course.CourseDto;
import com.keb.club_pila.model.response.BasicResponse;
import com.keb.club_pila.model.response.CommonResponse;
import com.keb.club_pila.model.response.ErrorResponse;
import com.keb.club_pila.service.CourseService;
import com.keb.club_pila.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@RestController
public class CourseApiController {
    private final CourseService courseService;
    private final MemberService memberService;

    @PostMapping("/api/v1/admin/course")
    public ResponseEntity<? extends BasicResponse> save(@RequestBody CourseDto.CourseSaveRequestDto courseSaveRequestDto) {
        Long result = courseService.courseSave(courseSaveRequestDto);
        if (result != 0) {
            return ResponseEntity.created(URI.create("/api/v1/course/" + result)).build();
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("잘못된 선생님 수업 추가 요청"));
    }

    @GetMapping("/api/v1/course")
    public ResponseEntity<? extends BasicResponse> findAllCourses() {
        List<CourseDto.CourseResponseDto> course = courseService.findAllCourses();

        if (course.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(new CommonResponse<>(course));
    }

//해당 회원이 신청한 수업 리스트 쫙 뜨기
    @GetMapping("/api/v1/course/me/{id}") //내가 들은 수업 리스트 쫙..
    public ResponseEntity<? extends BasicResponse> findMyCourses(@PathVariable Long id){
           List<CourseDto.CourseTeacherResponseDto> result= memberService.findCoursesById(id);
           if(!result.isEmpty()){
               System.out.println("here"+result.toString());
               return ResponseEntity.ok().body(new CommonResponse<>(result));
           }
           else {
               return ResponseEntity.ok().body(new CommonResponse<>(result));
           }

    }


    //기간 내 모든 수업 조회
    @GetMapping("/api/v1/course/search")
    public ResponseEntity<? extends BasicResponse> findCourseBetween(@RequestParam("start")
                                                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                                 LocalDateTime start, @RequestParam("end")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime end){
        System.out.println("start:: "+ start);
        System.out.println("end:: "+ end);
        List<CourseDto.CourseResponseDto> result=courseService.findCourseBetween(start, end);
            return ResponseEntity.ok().body(new CommonResponse<>(result));
    }

//해당 아이디를 가진 수업 조회
    @GetMapping("/api/v1/admin/course/{id}")
    public ResponseEntity<? extends BasicResponse> findById(@PathVariable Long id) {

        CourseDto.CourseResponseDto courseResponseDto = courseService.findById(id);

        if (courseResponseDto.getId() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("잘못된 수업 아이디 조회 요청: " + id));
        }
        return ResponseEntity.ok().body(new CommonResponse<>(courseResponseDto));
    }

    @PutMapping("/api/v1/admin/course/{id}")
    public ResponseEntity<? extends BasicResponse> updateById(@PathVariable Long id, @RequestBody CourseDto.CourseUpdateDto courseUpdateDto) {
        Long result = courseService.updateById(id, courseUpdateDto);
        if (result == 0L) { // 실패시
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("잘못된 수업 아이디 갱신 요청: " + id));
        }
        //성공시
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/api/v1/admin/course/{id}")
    public ResponseEntity<? extends BasicResponse> deleteById(@PathVariable Long id) {
        boolean result = courseService.deleteById(id);
        if (!result) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("일치하는 수업 아이디 조회 실패: " + id));
        }
        return ResponseEntity.noContent().build();
    }



/*

    @PutMapping("/api/v1/course/{id}")
    public Long update(@PathVariable Long id, @RequestBody CourseUpdateRequestDto requestDto) {
        return courseService.update(id, requestDto);
    }

    @GetMapping("/api/v1/course/teacher/{teacher}")
    public List<CourseDto> findByTeacher(@PathVariable(value = "teacher") String teacher) {
        System.out.println("findByTeacher");
        return converter.entityToDto(courseService.findByTeacher(teacher));
    }
 @GetMapping("/api/v1/course/{id}")
     public ResponseEntity<?> findById(@PathVariable("id") Long id) {
 //        Course entity = courseService.findById(id);
 //        return new ResponseEntity<CourseDto>(converter.entityToDto(entity), HttpStatus.OK);*/


}