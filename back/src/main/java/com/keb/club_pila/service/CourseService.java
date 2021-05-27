package com.keb.club_pila.service;

import com.keb.club_pila.dto.course.CourseDto;
import com.keb.club_pila.model.entity.course.Course;
import com.keb.club_pila.model.entity.course.Teacher;
import com.keb.club_pila.repository.CourseRepository;
import com.keb.club_pila.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;

    @Transactional
    public Long courseSave(CourseDto.CourseSaveRequestDto courseSaveRequestDto) {
        //디티오 받아서 레포지토리에 아이디로 사람 찾고, 그 다음에...
        //수업 추가하는 로직
        Optional<Teacher> teacher = teacherRepository.findById(courseSaveRequestDto.getTeacher_id());
        //티처 id에 맞는 엔티티를 찾았다면, 다음 진행
        if (teacher.isPresent()) {
            Teacher foundTeacher=teacher.get();
            Course course = courseSaveRequestDto.toEntity(foundTeacher);

            foundTeacher.getCourses().add(course);

            courseRepository.save(course);
            return course.getId();
        }
        //티처 id에 맞는 엔티티를 찾지 못했다면 0L 반환.
        else {
            return 0L;
        }
    }

    @Transactional(readOnly = true)
    public List<CourseDto.CourseTeacherResponseDto> findCourseBetween(LocalDateTime start, LocalDateTime end){
        List<Course> courses=courseRepository.findAllByCourseDateTimeBetween(start, end);
        System.out.println("list 개수::"+courses.size());
        return courses.stream().map(course->new CourseDto.CourseTeacherResponseDto(course)).collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<CourseDto.CourseResponseDto> findAllCourses() {

        List<Course> courses = courseRepository.findAll();
        //리스트로 받아온 거 하나하나 이제 디티오로 변환해줘야 함.
        return courses.stream().map(course ->
                new CourseDto.CourseResponseDto(course)
        ).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CourseDto.CourseResponseDto findById(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            return new CourseDto.CourseResponseDto(course.get());
        } else return new CourseDto.CourseResponseDto();


    }

    @Transactional
    public boolean deleteById(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        if (!course.isPresent())
            return false;
        courseRepository.deleteById(id);
        return true;
    }

    @Transactional
    public Long updateById(Long id, CourseDto.CourseUpdateDto courseUpdateDto) {
        Optional<Course> course = courseRepository.findById(id);
        if (!course.isPresent())
            return 0L;
        else {
            course.get().update(courseUpdateDto.getTitle(), courseUpdateDto.getContent());
            return id;
        }
    }


    /*public List<Course> findByTeacher(String teacher) {
        List<Course> courses = courseRepository.findByTeacher(teacher);
        return courseRepository.findByTeacher(teacher);
    }*/
}
