package com.keb.club_pila.dto.course;

import com.keb.club_pila.model.entity.course.Course;
import com.keb.club_pila.model.entity.course.Teacher;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

public class CourseDto {
    @Getter
    @NoArgsConstructor
    public static class CourseResponseDto {
        private Long id;
        private String title;
        private String content;
        private String teacher_name;

        public CourseResponseDto(Course entity) { //entitiy->dto 로 convert!!
            this.id = entity.getId();
            this.title = entity.getTitle();
            this.content = entity.getContent();
            this.teacher_name = entity.getTeachers().getName();
        }


    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CourseSaveRequestDto {
        private String title;
        private String content;
        private Long teacher_id;
        private LocalDateTime courseTime;

        public Course toEntity(Teacher teacher) {
            return Course.builder()
                    .title(title)
                    .content(content)
                    .teachers(teacher)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class CourseUpdateDto {
        private String title;
        private String content;
        @Builder
        public CourseUpdateDto(String title, String content){
            this.title = title;
            this.content = content;
        }
    }
}
