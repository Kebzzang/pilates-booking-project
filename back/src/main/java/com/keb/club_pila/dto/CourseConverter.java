package com.keb.club_pila.dto;

import com.keb.club_pila.dto.course.CourseDto;
import com.keb.club_pila.model.entity.course.Course;
import com.keb.club_pila.model.entity.course.Teacher;
import com.keb.club_pila.repository.CourseRepository;
import com.keb.club_pila.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Component
public class CourseConverter {
    private final TeacherRepository teacherRepository;
    public CourseDto entityToDto(Course course) {
        CourseDto dto = CourseDto.builder()
                .id(course.getId())
                .title(course.getTitle())
                .content(course.getContent())
                .teacher_id(course.getTeachers().getId())
              //  .courseTime(course.getCourseTime())
                .build();

        return dto;
    }

    public List<CourseDto> entityToDto(List<Course> courses) {
        return courses.stream().map(course -> entityToDto(course)).collect(Collectors.toList());
    }

    public Course dtoToEntity(CourseDto dto) {
//dto에는 일단 선생님 id 들어온단 말이지??그럼 이제 선생님 엔티티를 찾아서 넣어줘야 하는 겅 ㅏ니니??
        Optional<Teacher> teacher=teacherRepository.findById(dto.getTeacher_id());
        if(teacher.isPresent()){Teacher foundTeacher=teacher.get();
        foundTeacher.adding(Course.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .teachers(foundTeacher)
                //  .courseTime(dto.getCourseTime())
                .build());
        return Course.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .teachers(foundTeacher)
                //  .courseTime(dto.getCourseTime())
                .build();}
        return new Course();
    }

    public List<Course> dtoToEntity(List<CourseDto> courseDtos) {
        return courseDtos.stream().map(dto -> dtoToEntity(dto)).collect(Collectors.toList());
    }

}