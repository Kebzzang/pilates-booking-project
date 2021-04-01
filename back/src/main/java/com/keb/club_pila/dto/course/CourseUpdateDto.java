package com.keb.club_pila.dto.course;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseUpdateDto {
    private String title;
    private String content;
}
