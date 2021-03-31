package com.keb.club_pila.dto;

import com.keb.club_pila.dto.course.CourseDto;
import com.keb.club_pila.model.entity.course.Course;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseConverter {

    public CourseDto entityToDto(Course course) {
        CourseDto dto = CourseDto.builder()
                .id(course.getId())
                .title(course.getTitle())
                .content(course.getContent())
                .teacher(course.getTeacher())
                .build();

        return dto;
    }

    public List<CourseDto> entityToDto(List<Course> courses) {
        return courses.stream().map(course -> entityToDto(course)).collect(Collectors.toList());
    }

    public Course dtoToEntity(CourseDto dto) {
        return Course.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .teacher(dto.getTeacher())
                .build();
    }

    public List<Course> dtoToEntity(List<CourseDto> courseDtos) {
        return courseDtos.stream().map(dto -> dtoToEntity(dto)).collect(Collectors.toList());
    }

}
