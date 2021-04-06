package com.keb.club_pila.service;

import com.keb.club_pila.dto.joininfo.JoinInfoDto;
import com.keb.club_pila.model.entity.course.Course;
import com.keb.club_pila.model.entity.join.JoinInfo;
import com.keb.club_pila.model.entity.user.User;
import com.keb.club_pila.repository.CourseRepository;
import com.keb.club_pila.repository.JoinInfoRepository;
import com.keb.club_pila.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JoinInfoService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final JoinInfoRepository joinInfoRepository;
    @Transactional
    public Long joininfoSave(JoinInfoDto.JoinInfoSaveRequestDto joinInfoSaveRequestDto){
        Optional<User> user=userRepository.findById(joinInfoSaveRequestDto.getUser_id());
        Optional<Course> course=courseRepository.findById(joinInfoSaveRequestDto.getCourse_id());

        if(user.isPresent() && course.isPresent())
        {
            JoinInfo joinInfo=JoinInfo.builder()
                    .course(course.get())
                    .user(user.get())
                    .build();
            //유
            joinInfoRepository.save(joinInfo);
            return course.get().getId();
        }

        return 0L;
    }
}
