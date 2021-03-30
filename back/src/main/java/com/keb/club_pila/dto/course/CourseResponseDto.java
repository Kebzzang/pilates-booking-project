package com.keb.club_pila.dto.course;

import com.keb.club_pila.domain.courses.Course;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

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
    @Builder
    public CourseResponseDto(String title, String content, String teacher) {
        this.title=title;
        this.content=content;
        this.teacher=teacher;

    }
    public CourseResponseDto toDto(){
        return CourseResponseDto.builder()
                .title(title)
                .content(content)
                .teacher(teacher)
                .build();
    }
}