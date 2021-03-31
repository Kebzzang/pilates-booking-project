package com.keb.club_pila.dto.course;

import com.keb.club_pila.entity.course.Course;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CourseResponseDto {

    private Long id;
    private String title;
    private String content;
    private String teacher;

    public CourseResponseDto(Course entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.teacher = entity.getTeacher();
    }


}