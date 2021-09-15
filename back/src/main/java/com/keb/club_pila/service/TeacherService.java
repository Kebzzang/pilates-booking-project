package com.keb.club_pila.service;

import com.keb.club_pila.config.bucket.BucketName;
import com.keb.club_pila.config.filestore.FileStore;
import com.keb.club_pila.dto.teacher.TeacherDto;
import com.keb.club_pila.model.entity.course.Teacher;
import com.keb.club_pila.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.apache.http.entity.ContentType;
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
    public Long teacherSave(TeacherDto.TeacherSaveRequestDto teacherSaveRequestDto, MultipartFile file){
        isFileEmpty(file);
        isImage(file);
        Map<String, String> metadata = extractMetadata(file);

        String filename=String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
        try{

            Teacher teacher = teacherSaveRequestDto.toEntity(filename);
            teacherRepository.save(teacher);
            String path= String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), teacher.getId());
            fileStore.save(path, filename, Optional.of(metadata),file.getInputStream());
            return teacher.getId();
        }catch(IOException e){
            throw new IllegalStateException(e);
        }



    }

    @Transactional
    public boolean deleteById(Long id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if (teacher.isEmpty())
            return false;
        teacherRepository.deleteById(id);
        return true;
    }

    @Transactional(readOnly = true)
    public List<TeacherDto.TeacherResponseDto> findAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        return teachers.stream().map(TeacherDto.TeacherResponseDto::new
        ).collect(Collectors.toList());
    }

    @Transactional(readOnly=true)
    //현재 일하고 있는 isWorking=1인 선생님 조회
    public List<TeacherDto.TeacherResponseSimpleDto> findAllWorkingTeachersSimple(){
        List <Teacher> teachers=teacherRepository.findByWorkingIsTrue();
        return teachers.stream().map(TeacherDto.TeacherResponseSimpleDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly=true)
    public TeacherDto.TeacherResponseDto findById(Long id){
        Optional<Teacher> teacher=teacherRepository.findById(id);
        return teacher.map(TeacherDto.TeacherResponseDto::new).orElseGet(TeacherDto.TeacherResponseDto::new);
    }
    //이미지 업데이트
    @Transactional
    public void uploadTeacherProfileImage(Long id, MultipartFile file) {
        //1. 파일이 비었는가?
        isFileEmpty(file);
        //2. 이미지 파일인가?
        isImage(file);
        //3. 해당 아이디 유저가 존재하는가?
        Teacher teacher=teacherRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 선생님이 존재하지 않습니다"));
        //4. 파일로부터 메타데이터 획득
        Map<String, String> metadata = extractMetadata(file);
        //5. 이미지를 s3에 저장하고 db의 프로필 url을 변경시킴
        String path= String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), teacher.getId());
        String filename=String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
        if(teacher.getUserProfileImageLink()!=null) {
            fileStore.delete(path, teacher.getUserProfileImageLink());
        }
        try {
            fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
            teacher.updateTeacherProfileImageLink(filename);

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
    //이미지 외 정보를 업데이트하기
    @Transactional
    public Long updateById(Long id, TeacherDto.TeacherUpdateDto teacherUpdateDto){
        Optional<Teacher> teacher=teacherRepository.findById(id);
        if(teacher.isEmpty())
            return 0L;
        else{
            teacher.get().update(teacherUpdateDto.getName(), teacherUpdateDto.isWorking(), teacherUpdateDto.getEmail(), teacherUpdateDto.getAbout());
            return id;
        }
    }



    public byte[] downloadTeacherProfileImage(Long id) {
        Teacher teacher=teacherRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 선생님이 존재하지 않습니다"));
      //  String path=String.format("%s/%s", BUcket, teacher.getId());

        String path= String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), teacher.getId());
        System.out.println("path :::"+path);
        System.out.println("getUserProfileLink:::"+teacher.getUserProfileImageLink().isEmpty());
        if(!teacher.getUserProfileImageLink().isEmpty()) {
            System.out.println("problem0:::");
            return fileStore.downloadFromS3(path, teacher.getUserProfileImageLink());
        }
        else
            return new byte[0];

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
