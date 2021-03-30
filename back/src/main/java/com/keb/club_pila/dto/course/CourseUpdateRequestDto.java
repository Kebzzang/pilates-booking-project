package com.keb.club_pila.dto.course;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CourseUpdateRequestDto {
    private String title;
    private String content;

    @Builder
    public CourseUpdateRequestDto (String  title, String content){
        this.title=title;
        this.content=content;
    }
}