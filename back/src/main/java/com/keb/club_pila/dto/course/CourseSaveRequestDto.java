package com.keb.club_pila.dto.course;

import com.keb.club_pila.model.entity.course.Course;
import com.keb.club_pila.model.entity.course.Teacher;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CourseSaveRequestDto {
    private String title;
    private String content;
    private Long teacher_id;
    private LocalDateTime courseTime;

    public CourseSaveRequestDto(String title, String content, Long teacher_id, LocalDateTime courseTime) {
        this.title = title;
        this.courseTime = courseTime;
        this.teacher_id = teacher_id;
        this.content = content;
    }

    public Course toEntity(Teacher teacher) {
        return Course.builder()
                .title(title)
                .content(content)
                .teachers(teacher)
                .build();
    }
}
