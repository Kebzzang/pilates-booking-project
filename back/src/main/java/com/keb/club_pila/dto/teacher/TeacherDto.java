package com.keb.club_pila.dto.teacher;


import com.keb.club_pila.dto.course.CourseDto;
import com.keb.club_pila.model.entity.course.Teacher;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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
        private String about;
        public TeacherResponseDto(Teacher entity) {
            this.id = entity.getId();
            this.name=entity.getName();
            this.about=entity.getAbout();
            this.courses =entity.getCourses().stream().map(CourseDto.CourseTeacherResponseDto::new).collect(Collectors.toList());
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
        private String about;
        public TeacherResponseSimpleDto(Teacher entity){
            this.id=entity.getId();
            this.name=entity.getName();
            this.email=entity.getEmail();
            this.about=entity.getAbout();
            this.userProfileImageLink= Optional.ofNullable(entity.getUserProfileImageLink());
        }

    }
   

    @Getter
    @NoArgsConstructor
    //@AllArgsConstructor
    public static class TeacherSaveRequestDto {
        private String name;
        private String email;
        private String about;
        public Teacher toEntity(String userProfileImageLink) {
            return Teacher.builder()
                    .userProfileImageLink(userProfileImageLink)
                    .name(name)
                    .email(email)
                    .about(about)
                    .working(true)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class TeacherUpdateDto {
        private String name;
        private boolean working;
        private String email;
        private String about;
        @Builder
        public TeacherUpdateDto(String name, boolean working, String email, String about) {
            this.name = name;
            this.working=working;
            this.email=email;
            this.about=about;
        }
    }
    @Getter
    @NoArgsConstructor
    public static class TeacherProfileImageUploadDto{

    }
}
