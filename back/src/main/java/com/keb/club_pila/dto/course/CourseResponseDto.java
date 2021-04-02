package com.keb.club_pila.dto.course;

import com.keb.club_pila.model.entity.course.Course;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CourseResponseDto {
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
