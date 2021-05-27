package com.keb.club_pila.dto.teacher;


import com.keb.club_pila.dto.course.CourseDto;
import com.keb.club_pila.model.entity.course.Teacher;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

public class TeacherDto {
    @Getter
    @NoArgsConstructor
    public static class TeacherResponseDto {
        private Long id;
        private String name;
        private List<CourseDto.CourseTeacherResponseDto> courses;
        private boolean isWorking;

        public TeacherResponseDto(Teacher entity) {
            this.id = entity.getId();
            this.name=entity.getName();
            this.courses =entity.getCourses().stream().map(course-> new CourseDto.CourseTeacherResponseDto(course)).collect(Collectors.toList());
            this.isWorking = entity.isWorking();

        }
    }
   

    @Getter
    @NoArgsConstructor
    //@AllArgsConstructor
    public static class TeacherSaveRequestDto {
        private String name;

        public Teacher toEntity() {
            return Teacher.builder()
                    .name(name)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class TeacherUpdateDto {
        private String name;
        private boolean working;
        @Builder
        public TeacherUpdateDto(String name, boolean working) {
            this.name = name;
            this.working=working;
        }
    }
}
