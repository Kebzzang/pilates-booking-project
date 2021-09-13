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
    public boolean joininfoDelete(Long user_id, Long course_id){
        boolean check=joinInfoRepository.existsByCourse_IdAndMember_Id(course_id, user_id);
        Optional<Course> course=courseRepository.findById(course_id);
        if(check){
            joinInfoRepository.deleteByCourse_IdAndMember_Id(course_id, user_id);
            course.get().courseCancel();
            return true;
        }
        return false;

    }
    @Transactional
    public Long joininfoSave(JoinInfoDto.JoinInfoSaveCancelRequestDto joinInfoSaveCancelRequestDto){

        Optional<Member> user=userRepository.findById(joinInfoSaveCancelRequestDto.getUser_id());

        Optional<Course> course=courseRepository.findById(joinInfoSaveCancelRequestDto.getCourse_id());

        boolean check=joinInfoRepository.existsByCourse_IdAndMember_Id(joinInfoSaveCancelRequestDto.getCourse_id(), joinInfoSaveCancelRequestDto.getUser_id());

        if(!check &&user.isPresent() && course.isPresent())
        {
            if(course.get().getNowStudent()<=course.get().getMaxStudent()){
                JoinInfo joinInfo=JoinInfo.builder()
                    .course(course.get())
                    .member(user.get())
                    .build();
                course.get().courseJoin();
                joinInfoRepository.save(joinInfo);
                if(course.get().getNowStudent().equals(course.get().getMaxStudent()))
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
