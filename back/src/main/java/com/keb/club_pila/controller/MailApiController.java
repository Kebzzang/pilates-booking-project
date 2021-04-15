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

    @GetMapping("/api/v1/user/email/send")
    public void sendMail(@RequestBody UserDto.UserEmailDto dto) throws MessagingException {
        StringBuffer emailcontent = new StringBuffer();
        emailcontent.append("<!DOCTYPE html>");
        emailcontent.append("<html>");
        emailcontent.append("<head>");
        emailcontent.append("</head>");
        emailcontent.append("<body>");
        emailcontent.append(
                " <div" +
                        "	style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 400px; height: 600px; border-top: 4px solid #02b875; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">" +
                        "	<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">" +
                        "		<span style=\"font-size: 15px; margin: 0 0 10px 3px;\">Pilas</span><br />" +
                        "		<span style=\"color: #02b875\">메일인증</span> 안내입니다." +
                        "	</h1>\n" +
                        "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">" +
                        dto.getUsername() +
                        "		님 안녕하세요.<br />" +
                        "		Pilas 에 가입해 주셔서 진심으로 감사드립니다.<br />" +
                        "		아래 <b style=\"color: #02b875\">'메일 인증'</b> 버튼을 클릭하여 회원가입을 완료해 주세요.<br />" +
                        "		감사합니다." +
                        "	</p>" +
                        "	<a style=\"color: #FFF; text-decoration: none; text-align: center;\"" +
                        "	href=\"http://localhost:8080/api/v1/user/email/certified?username=" + dto.getUsername() + "&certified=" + dto.getCertified() + "\" target=\"_blank\">" +
                        "		<p" +
                        "			style=\"display: inline-block; width: 210px; height: 45px; margin: 30px 5px 40px; background: #02b875; line-height: 45px; vertical-align: middle; font-size: 16px;\">" +
                        "			메일 인증</p>" +
                        "	</a>" +
                        "	<div style=\"border-top: 1px solid #DDD; padding: 5px;\"></div>" +
                        " </div>"
        );
        emailcontent.append("</body>");
        emailcontent.append("</html>");
        emailService.sendMail(dto.getUsername(), "[Pilas 이메일 인증]", emailcontent.toString());
    }

    @GetMapping("/api/v1/user/email/certified")
    public ResponseEntity<? extends BasicResponse> updateCertified(@RequestParam("username") String username,@RequestParam("certified") String certified) {
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
