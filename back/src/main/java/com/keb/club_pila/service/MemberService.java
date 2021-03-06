package com.keb.club_pila.service;

import com.keb.club_pila.config.jwt.JwtProvider;
import com.keb.club_pila.config.jwt.SecurityUtil;
import com.keb.club_pila.config.oauth.provider.OAuthUserInfo;
import com.keb.club_pila.dto.course.CourseDto;
import com.keb.club_pila.dto.user.UserDto;
import com.keb.club_pila.model.entity.join.JoinInfo;
import com.keb.club_pila.model.entity.user.Member;
import com.keb.club_pila.model.entity.user.RoleType;
import com.keb.club_pila.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtProvider jwtProvider;

    //유저 회원 가입
    @Transactional
    public Long userSave(UserDto.UserSaveRequestDto userSaveRequestDto)  {
        Optional<Member> user = userRepository.findByUsername(userSaveRequestDto.getUsername());

        if (user.isEmpty()) {

            Member entity = userSaveRequestDto.toEntity(passwordEncoder.encode(userSaveRequestDto.getPassword()));

            userRepository.save(entity);

            //회원 가입 시 입력한 이메일로 인증메일 보냄
        //    emailService.sendEmail(entity.getEmail(), entity.getUsername(), entity.getCertified());
            return 1L;
        }
        return 0L;
    }
    //oauth 관련 회원 가입
    @Transactional
    public String oauthUserSave(OAuthUserInfo oauthUser) {

        Optional<Member> user = userRepository.findByUsername(oauthUser.getProvider() + "_" + oauthUser.getProviderId());
       // Member oauthuser=userRepository.findByUsername(oauthUser.getProvider() + "_" + oauthUser.getProviderId()).orElse(null);


        if (!user.isPresent()) //처음 로그인이라면 회원가입시켜주고, 토큰 발행해줌
        {
            Member member = Member.builder()
                    .username(oauthUser.getProvider() + "_" + oauthUser.getProviderId())
                    .password(passwordEncoder.encode("dsf2aA!qf"))
                    .email(oauthUser.getEmail())
                    .provider(oauthUser.getProvider())
                    .providerId(oauthUser.getProviderId())
                    .role(RoleType.ROLE_USER)
                    .certified("Y")
                    .build();
            userRepository.save(member);
            String jwtToken = jwtProvider.generateTokenforOAuth(member);
            return jwtToken;
        }
        else{ //한번 로그인 해봤다면 DB에 저장되어 있음 -> 토큰만 발행시켜주면 된다.
            return jwtProvider.generateTokenforOAuth(user.get());
        }



    }

    //지금 로그인한 유저 정보 찾아서 리턴
    @Transactional(readOnly = true)
    public UserDto.UserResponseDto getMyUserInfo() {
        return userRepository.findUserByUsername(SecurityUtil.getCurrentMemberUsername())
                .map(UserDto.UserResponseDto::new)
                .orElse(null);
    }
//    @Transactional(readOnly=true)
//    public boolean isLoggedIn(){
//        if(userRepository.findUserByUsername(SecurityUtil.getCurrentMemberUsername());
//        return true;
//        return false;
//    }
    //회원 삭제
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Member> user = userRepository.findById(id);
        if (!user.isPresent())
            return false;
        userRepository.deleteById(id);
        return true;
    }

    //모든 회원 정보 출력
    @Transactional(readOnly = true)
    public List<UserDto.UserResponseDto> findAllUsers() {
        List<Member> members = userRepository.findAll();
        return members.stream().map(user ->
                new UserDto.UserResponseDto(user)
        ).collect(Collectors.toList());
    }
    //특정 회원 정보 출력
    @Transactional(readOnly = true)
    public UserDto.UserResponseDto findById(Long id) {
        Optional<Member> users = userRepository.findById(id);
        if (users.isPresent()) {
            return new UserDto.UserResponseDto(users.get());
        } else return new UserDto.UserResponseDto();
    }

    //특정 유저가 신청한 수업 목록 조회
    @Transactional(readOnly = true)
    public List<CourseDto.CourseTeacherResponseDto> findCoursesById(Long id){
        Optional<Member> users=userRepository.findById(id);
        List<CourseDto.CourseTeacherResponseDto> result=new ArrayList<>();
        if(users.isPresent())
        {
            Member user=users.get();
            Iterator<JoinInfo> iterator=user.getJoinedCourses().iterator();
            while(iterator.hasNext()){
                JoinInfo join=iterator.next();
              result.add(new CourseDto.CourseTeacherResponseDto(join.getCourse()));
            }
            return result;
        }
        else{
            return null;
        }
    }
    //유저 롤 업데이트
    @Transactional
    public Long updateUserRole(Long id, UserDto.UserRoleUpdateDto userRoleUpdateDto){
        Optional<Member> user=userRepository.findById(id);
        if(user.isPresent()){
            Member member=user.get();
            member.updateRole( userRoleUpdateDto.getRole());
            return 1L;
        } //없으면 리턴 널
        else{
            return 0L;
        }

    }
    @Transactional(readOnly=true)
    public UserDto.UserResponseDto findByUsername(String username){
        return userRepository.findByUsername(username).map(UserDto.UserResponseDto::new).orElse(new UserDto.UserResponseDto());
    }


    @Transactional //이건 뭐...음...ㅎㅎ...
    public Long updateById(Long id, UserDto.UserUpdateDto userUpdateDto) {
        Optional<Member> user = userRepository.findById(id);
        if (user.isEmpty())
            return 0L;
        else {
            user.get().updatePassword(passwordEncoder.encode(userUpdateDto.getPassword()));
            return id;
        }
    }


}
