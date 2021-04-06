package com.keb.club_pila.service;

import com.keb.club_pila.dto.teacher.TeacherDto;
import com.keb.club_pila.model.entity.course.Teacher;
import com.keb.club_pila.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;

    @Transactional
    public Long teacherSave(TeacherDto.TeacherSaveRequestDto teacherSaveRequestDto) {
        Teacher teacher = teacherSaveRequestDto.toEntity();
        teacherRepository.save(teacher);
        return teacher.getId();
    }

    @Transactional
    public boolean deleteById(Long id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if (!teacher.isPresent())
            return false;
        teacherRepository.deleteById(id);
        return true;
    }

    @Transactional(readOnly = true)
    public List<TeacherDto.TeacherResponseDto> findAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        return teachers.stream().map(teacher ->
                new TeacherDto.TeacherResponseDto(teacher)
        ).collect(Collectors.toList());
    }

    @Transactional(readOnly=true)
    public TeacherDto.TeacherResponseDto findById(Long id){
        Optional<Teacher> teacher=teacherRepository.findById(id);
        if (teacher.isPresent()) {
            return new TeacherDto.TeacherResponseDto(teacher.get());
        } else return new TeacherDto.TeacherResponseDto();
    }

    @Transactional
    public Long updateById(Long id, TeacherDto.TeacherUpdateDto teacherUpdateDto){
        Optional<Teacher> teacher=teacherRepository.findById(id);
        if(!teacher.isPresent())
            return 0L;
        else{
            teacher.get().update(teacherUpdateDto.getName(), teacherUpdateDto.isWorking());
            return id;
        }
    }
}
