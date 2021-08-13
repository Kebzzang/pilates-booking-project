package com.keb.club_pila.controller;

import com.keb.club_pila.dto.joininfo.JoinInfoDto;
import com.keb.club_pila.dto.user.UserDto;
import com.keb.club_pila.model.response.BasicResponse;
import com.keb.club_pila.model.response.CommonResponse;
import com.keb.club_pila.model.response.ErrorResponse;
import com.keb.club_pila.service.JoinInfoService;
import com.keb.club_pila.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.http.protocol.ResponseServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserApiController {
    //회원가입 로직 (CRUD)
    //수업 신청 로직!!(create, delete 조회 까지만)
    private final JoinInfoService joinInfoService;
    private final MemberService userService;


    @PostMapping("/api/v1/user/course/join") //수업 참여 포스트 매핑 -> 학생만 가능. 가능한 학생 수 넘어서면
    public ResponseEntity<? extends BasicResponse> joinCourse(@RequestBody JoinInfoDto.JoinInfoSaveCancelRequestDto joinInfoSaveCancelRequestDto) {

        System.out.println("JoinInfo problem here1");
        Long result=joinInfoService.joininfoSave(joinInfoSaveCancelRequestDto);
        if(result!=0){
            return ResponseEntity.ok().body(new CommonResponse<>("신청 완료"));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("잘못된 요청"));
        }
    }
    @DeleteMapping("/api/v1/user/course/cancel")
    public ResponseEntity<? extends BasicResponse> cancelJoin(@RequestParam("user_id") Long user_id, @RequestParam("course_id") Long course_id) {
        System.out.println("user_id"+user_id);
        boolean result=joinInfoService.joininfoDelete(user_id, course_id);
        if(result){return ResponseEntity.noContent().build();}
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("잘못된 요청"));
        }

    }

        // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/api/v1/user/me")
    public ResponseEntity<? extends BasicResponse> getMyUserInfo(){
        UserDto.UserResponseDto result=userService.getMyUserInfo();
        if(result!=null)
        { return ResponseEntity.ok().body(new CommonResponse<>(result));}
        else
        { return ResponseEntity.ok().body(new CommonResponse<>(false));}
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
