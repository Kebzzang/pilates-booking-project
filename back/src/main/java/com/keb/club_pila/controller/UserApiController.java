package com.keb.club_pila.controller;

import com.keb.club_pila.dto.joininfo.JoinInfoDto;
import com.keb.club_pila.dto.user.UserDto;
import com.keb.club_pila.model.response.BasicResponse;
import com.keb.club_pila.model.response.CommonResponse;
import com.keb.club_pila.model.response.ErrorResponse;
import com.keb.club_pila.service.JoinInfoService;
import com.keb.club_pila.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class UserApiController {
    //회원가입 로직 (CRUD)
    //수업 신청 로직!!(create, delete 조회 까지만)
    private final JoinInfoService joinInfoService;
    private final UserService userService;

    @PostMapping("/api/v1/course/join") //수업 참여 포스트 매핑
    public ResponseEntity<? extends BasicResponse> joinCourse(@RequestBody JoinInfoDto.JoinInfoSaveRequestDto joinInfoSaveRequestDto) {
        Long result=joinInfoService.joininfoSave(joinInfoSaveRequestDto);
        if(result!=0){
            return ResponseEntity.created(URI.create("/api/v1/course/" + result)).build();
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("잘못된 수업 혹은 유저 수강 요청"));

        }
    }

    @PostMapping("/api/v1/user/join")
    public ResponseEntity<? extends BasicResponse> joinUser( UserDto.UserSaveRequestDto userSaveRequestDto){
        Long result= userService.userSave(userSaveRequestDto);
        if(result!=0){
            return ResponseEntity.created(URI.create("/api/v1/user/" + result)).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("중복 유저네임 가입 요청"));
    }
    @GetMapping("/api/v1/user/{id}")
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

    @DeleteMapping("/api/v1/course/{id}")
    public ResponseEntity<? extends BasicResponse> deleteById(@PathVariable Long id) {
        boolean result = userService.deleteById(id);
        if (!result) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("일치하는 유저 아이디 조회 실패: " + id));
        }
        return ResponseEntity.noContent().build();
    }

}
