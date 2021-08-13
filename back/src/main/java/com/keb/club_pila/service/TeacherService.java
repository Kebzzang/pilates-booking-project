package com.keb.club_pila.service;

import com.keb.club_pila.config.bucket.BucketName;
import com.keb.club_pila.config.filestore.FileStore;
import com.keb.club_pila.dto.teacher.TeacherDto;
import com.keb.club_pila.model.entity.course.Teacher;
import com.keb.club_pila.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.apache.http.entity.ContentType;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final FileStore fileStore;
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
    public List<TeacherDto.TeacherResponseSimpleDto> findAllTeachersSimple(){
        List <Teacher> teachers=teacherRepository.findAll();
        return teachers.stream().map(teacher->new TeacherDto.TeacherResponseSimpleDto(teacher)).collect(Collectors.toList());
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

    public void uploadTeacherProfileImage(Long id, MultipartFile file) {
        //1. check if imge is not empty
        isFileEmpty(file);
        //2. if file is an image
        isImage(file);
        //3. this teacher exists in our db
        Optional<Teacher> teacher=teacherRepository.findById(id);
        if (teacher.isPresent()) {
            //4. Grap some metadata from file if any
            Map<String, String> metadata = extractMetadata(file);
            //5. Store the iamge in s3 and update db with s3 image link
            System.out.println("techer id: " +teacher.get().getId());
            System.out.println("bucekt name: " +BucketName.PROFILE_IMAGE.getBucketName());
            String path= String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), teacher.get().getId());
            System.out.println("path: "+path);
            String filename=String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
            try {
                fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

    }

    private Map<String, String> extractMetadata(MultipartFile file) {
        Map<String, String> metadata=new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }

    private void isImage(MultipartFile file) {
        if(!Arrays.asList(
                ContentType.IMAGE_JPEG.getMimeType(),
                ContentType.IMAGE_PNG.getMimeType(),
                ContentType.IMAGE_GIF.getMimeType()).contains(file.getContentType()))
        {
            throw new IllegalStateException("파일 형식은 PNG, GIF, JPEG만 가능합니다"+": "+file.getContentType());
        }
    }

    private void isFileEmpty(MultipartFile file) {
        if(file.isEmpty()){
            throw new IllegalStateException("이미지가 비었습니다 ["+ file.getSize()+"]" );
        }
    }
}
