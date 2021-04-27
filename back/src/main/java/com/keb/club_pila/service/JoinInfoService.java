package com.keb.club_pila.service;

import com.keb.club_pila.dto.joininfo.JoinInfoDto;
import com.keb.club_pila.model.entity.course.Course;
import com.keb.club_pila.model.entity.join.JoinInfo;
import com.keb.club_pila.model.entity.user.Member;
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
        Optional<Member> user=userRepository.findById(joinInfoSaveRequestDto.getUser_id());
        Optional<Course> course=courseRepository.findById(joinInfoSaveRequestDto.getCourse_id());
        //등록된 학생 수 몇 명인지 확인하기
        //joininfod에서...
        Long students=joinInfoRepository.countAllByCourse_id(joinInfoSaveRequestDto.getCourse_id());
        System.out.println(":::students"+students);
        if(user.isPresent() && course.isPresent())
        {
            if(students<course.get().getMaxStudent()){
                JoinInfo joinInfo=JoinInfo.builder()
                    .course(course.get())
                    .member(user.get())
                    .build();

                joinInfoRepository.save(joinInfo);
                if(students==(course.get().getMaxStudent()-1))
                {
                    //then..
                    course.get().isLocked();
                }
                return course.get().getId();
            }else{
                //맥스 더이상 수강신청 불가
                return 0L;
            }

        }

        return 0L;
    }
}
