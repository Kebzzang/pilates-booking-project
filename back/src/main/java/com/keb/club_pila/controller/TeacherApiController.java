package com.keb.club_pila.controller;

import com.keb.club_pila.dto.course.CourseDto;
import com.keb.club_pila.dto.teacher.TeacherDto;
import com.keb.club_pila.model.response.BasicResponse;
import com.keb.club_pila.model.response.CommonResponse;
import com.keb.club_pila.model.response.ErrorResponse;
import com.keb.club_pila.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class TeacherApiController {
    private final TeacherService teacherService;

    /*
    1. 선생님 등록
    2. 선생님 삭제
    3. 선생님 업데이트
    4. 선생님 읽기 - 모든 선생님, 특정 아이디 선생님 -> 딸린 수업까지 전부 ?

    */
    @PostMapping("/api/v1/teacher")
    public ResponseEntity<? extends BasicResponse> save(@RequestBody TeacherDto.TeacherSaveRequestDto teacherSaveRequestDto) {
        Long result = teacherService.teacherSave(teacherSaveRequestDto);
        return ResponseEntity.created(URI.create("/api/v1/teacher/" + result)).build();
    }

    @DeleteMapping("/api/v1/teacher/{id}")
    public ResponseEntity<? extends BasicResponse> delete(@PathVariable Long id) {
        boolean result=teacherService.deleteById(id);
        if (!result) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("일치하는 수업 아이디 조회 실패: " + id));
        }
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/api/v1/teacher")
    public ResponseEntity<? extends BasicResponse> findAllTeachers() {
        List<TeacherDto.TeacherResponseDto> teachers=teacherService.findAllTeachers();
        if (teachers.isEmpty()) {
            System.out.println("here1");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(new CommonResponse<>(teachers));

    }

    @GetMapping("/api/v1/teacher/{id}")
    public ResponseEntity<? extends BasicResponse> findById(@PathVariable Long id) {
        TeacherDto.TeacherResponseDto teacherResponseDto = teacherService.findById(id);

        if (teacherResponseDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("잘못된 선생님 조회 요청: " + id));
        }
        return ResponseEntity.ok().body(new CommonResponse<TeacherDto.TeacherResponseDto>(teacherResponseDto));

    }

    @PutMapping("/api/v1/teacher/{id}")
    public ResponseEntity<? extends BasicResponse> update(@PathVariable Long id, @RequestBody TeacherDto.TeacherUpdateDto teacherUpdateDto) {
        Long result = teacherService.updateById(id, teacherUpdateDto);
        if (result==0L) { // 실패시
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("잘못된 선생님 갱신 요청: "+id));
        }
        //성공시
        return ResponseEntity.noContent().build();
    }
}
