package com.keb.club_pila.service;

import com.keb.club_pila.domain.courses.Course;
import com.keb.club_pila.dto.course.CourseResponseDto;
import com.keb.club_pila.dto.course.CourseSaveRequestDto;
import com.keb.club_pila.dto.course.CourseUpdateRequestDto;
import com.keb.club_pila.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private  final CourseRepository courseRepository;

    @Transactional
    public Long save(CourseSaveRequestDto requestDto){
        return courseRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, CourseUpdateRequestDto requestDto){
        Course course=courseRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 수업이 없습니다. id="+id));
        course.update(requestDto.getTitle(), requestDto.getContent());

        return id;

    }
    
    public CourseResponseDto findById(Long id){
        Course entity=courseRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 수업이 없습니다. id="+id));

        return new CourseResponseDto(entity);
    }

    public List<CourseResponseDto> findByTeacher(String teacher){
        List<Course> courses=courseRepository.findByTeacher(teacher);
        List<CourseResponseDto> courseResponseDtoList=new ArrayList<>();

        if(courses.isEmpty()) return courseResponseDtoList;

        for(Course course: courses){
           courseResponseDtoList.add(new CourseResponseDto(course).toDto());
        }

        return courseResponseDtoList;
    }
}
