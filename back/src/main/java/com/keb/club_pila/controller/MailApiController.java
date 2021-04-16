package com.keb.club_pila.controller;


import com.keb.club_pila.dto.user.UserDto;
import com.keb.club_pila.model.response.BasicResponse;
import com.keb.club_pila.model.response.CommonResponse;
import com.keb.club_pila.model.response.ErrorResponse;
import com.keb.club_pila.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RequiredArgsConstructor
@RestController
public class MailApiController {

    private final EmailService emailService;




    @GetMapping("/api/v1/user/email/certified")
    public ResponseEntity<? extends BasicResponse> updateCertified
            (@RequestParam("username") String username,@RequestParam("certified") String certified) {
        //찾아서 맞으면 이제 certified를 Y으로 변경시켜줌
        if (emailService.updateCertified(username, certified) == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("잘못된 인증 요청"));
        }
        else {
            SecurityContextHolder.getContext().setAuthentication(null);
            return ResponseEntity.ok().body(new CommonResponse<>("인증 완료"));
        }
    }

}
