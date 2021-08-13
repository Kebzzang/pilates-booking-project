package com.keb.club_pila.dto.teacher;


import com.keb.club_pila.dto.course.CourseDto;
import com.keb.club_pila.model.entity.course.Teacher;
import lombok.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TeacherDto {
    @Getter
    @NoArgsConstructor
    public static class TeacherResponseDto {
        private Long id;
        private String name;
        private List<CourseDto.CourseTeacherResponseDto> courses;
        private boolean isWorking;
        private Optional<String> userProfileImageLink;
        private String email;

        public TeacherResponseDto(Teacher entity) {
            this.id = entity.getId();
            this.name=entity.getName();
            this.courses =entity.getCourses().stream().map(course-> new CourseDto.CourseTeacherResponseDto(course)).collect(Collectors.toList());
            this.isWorking = entity.isWorking();
            this.userProfileImageLink= Optional.ofNullable(entity.getUserProfileImageLink());
            this.email= entity.getEmail();
        }
    }
    @Getter
    @NoArgsConstructor
    public static class TeacherResponseSimpleDto{
        private Long id;
        private String name;
        private Optional<String> userProfileImageLink;
        private String email;
        public TeacherResponseSimpleDto(Teacher entity){
            this.id=entity.getId();
            this.name=entity.getName();
            this.email=entity.getEmail();
            this.userProfileImageLink= Optional.ofNullable(entity.getUserProfileImageLink());
        }

    }
   

    @Getter
    @NoArgsConstructor
    //@AllArgsConstructor
    public static class TeacherSaveRequestDto {
        private String name;
        private String email;
        public Teacher toEntity() {
            return Teacher.builder()
                    .name(name)
                    .email(email)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class TeacherUpdateDto {
        private String name;
        private boolean working;
        private String email;
        @Builder
        public TeacherUpdateDto(String name, boolean working, String email) {
            this.name = name;
            this.working=working;
            this.email=email;
        }
    }
    @Getter
    @NoArgsConstructor
    public static class TeacherProfileImageUploadDto{

    }
}
