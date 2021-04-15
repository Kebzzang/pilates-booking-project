package com.keb.club_pila.controller;

import com.keb.club_pila.config.jwt.JwtFilter;
import com.keb.club_pila.config.jwt.JwtProvider;
import com.keb.club_pila.dto.joininfo.JoinInfoDto;
import com.keb.club_pila.dto.user.LoginDto;
import com.keb.club_pila.dto.user.TokenDto;
import com.keb.club_pila.dto.user.UserDto;
import com.keb.club_pila.model.response.BasicResponse;
import com.keb.club_pila.model.response.CommonResponse;
import com.keb.club_pila.model.response.ErrorResponse;
import com.keb.club_pila.service.JoinInfoService;
import com.keb.club_pila.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.net.URI;

@RequiredArgsConstructor
@RestController
public class UserApiController {
    //회원가입 로직 (CRUD)
    //수업 신청 로직!!(create, delete 조회 까지만)
    private final JoinInfoService joinInfoService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberService userService;
    private final JwtProvider jwtProvider;


    @PostMapping("/api/v1/user/course/join") //수업 참여 포스트 매핑 -> 학생만...?
    public ResponseEntity<? extends BasicResponse> joinCourse(@RequestBody JoinInfoDto.JoinInfoSaveRequestDto joinInfoSaveRequestDto) {
        Long result=joinInfoService.joininfoSave(joinInfoSaveRequestDto);
        if(result!=0){
            return ResponseEntity.created(URI.create("/api/v1/course/" + result)).build();
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("잘못된 수업 혹은 유저 수강 요청"));

        }
    }
    @PostMapping("/api/v1/auth")
    public ResponseEntity<? extends BasicResponse> authUser(@RequestBody LoginDto loginDto){
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication authentication=authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt=jwtProvider.generateToken(authentication);

        HttpHeaders headers=new HttpHeaders();
        headers.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer "+jwt);

        return ResponseEntity.ok().body(new CommonResponse<>(new TokenDto(jwt)));

    }
    @PostMapping("/api/v1/signup")
    public ResponseEntity<? extends BasicResponse> joinUser(@RequestBody UserDto.UserSaveRequestDto userSaveRequestDto) throws MessagingException {
         Long result= userService.userSave(userSaveRequestDto);
        if(result!=0){
            return ResponseEntity.created(URI.create("/api/v1/user/" + result)).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("중복 유저네임 가입 요청"));
    }
   // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/api/v1/user/me")
    public ResponseEntity<? extends BasicResponse> getMyUserInfo(){

        return ResponseEntity.ok().body(new CommonResponse<>(userService.getMyUserInfo()));
    }
    @GetMapping("/api/v1/admin/user/{id}")
    public ResponseEntity<? extends BasicResponse> findById(@PathVariable Long id) {

        UserDto.UserResponseDto userResponseDto = userService.findById(id);

        if (userResponseDto.getId()==null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("잘못된 수업 아이디 조회 요청: " + id));
        }
        return ResponseEntity.ok().body(new CommonResponse<>(userResponseDto));
    }
    @PutMapping("/api/v1/user/{id}")
    public ResponseEntity<? extends BasicResponse> updateById(@PathVariable Long id, @RequestBody UserDto.UserUpdateDto userUpdateDto) {
        Long result = userService.updateById(id, userUpdateDto);
        if (result==0L) { // 실패시
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("잘못된 유저 갱신 요청: "+id));
        }
        //성공시
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/api/v1/admin/user/{id}")
    public ResponseEntity<? extends BasicResponse> deleteById(@PathVariable Long id) {
        boolean result = userService.deleteById(id);
        if (!result) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("일치하는 유저 아이디 조회 실패: " + id));
        }
        return ResponseEntity.noContent().build();
    }

}
