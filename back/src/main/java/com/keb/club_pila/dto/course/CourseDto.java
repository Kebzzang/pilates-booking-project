package com.keb.club_pila.dto.course;

import com.keb.club_pila.model.entity.course.Teacher;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CourseDto {
    private Long id;
    private String title;
    private String content;
    private Long teacher_id;
    private LocalDateTime courseTime;
}
