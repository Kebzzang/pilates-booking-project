package com.keb.club_pila.dto.course;

import com.keb.club_pila.model.entity.course.Course;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CourseSaveRequestDto {
    private String title;
    private String content;
    private String teacher;

    @Builder
    public CourseSaveRequestDto(String title, String content, String teacher ){
        this.title=title;
        this.content=content;
        this.teacher=teacher;

    }

    public Course toEntity(){
        return Course.builder()
                .title(title)
                .content(content)
                .teacher(teacher)
                .build();
    }
}
