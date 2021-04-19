package com.keb.club_pila.controller;

import com.keb.club_pila.config.jwt.JwtFilter;
import com.keb.club_pila.config.jwt.JwtProvider;
import com.keb.club_pila.config.oauth.provider.GoogleUser;
import com.keb.club_pila.config.oauth.provider.OAuthUserInfo;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.net.URI;
import java.util.Map;


@RequiredArgsConstructor
@RestController
public class AuthApiController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberService userService;
    private final JwtProvider jwtProvider;

    //일반 로그인 요청
    @PostMapping("/api/v1/auth")
    public ResponseEntity<TokenDto> authUser(@RequestBody LoginDto loginDto) {

        if(userService.findByUsername(loginDto.getUsername())!=null) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtProvider.generateToken(authentication);
            System.out.println(jwt);
            HttpHeaders headers = new HttpHeaders();
            headers.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
            System.out.println(headers);

            return new ResponseEntity<>(new TokenDto(jwt), headers, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    //구글 소셜 로그인 요청 시
    @PostMapping("/api/v1/oauth/google")
    public ResponseEntity<? extends BasicResponse> oauth(@RequestBody Map<String, Object> data) {
        System.out.println("jwt oauth");
        System.out.println(data.get("profileObj"));
        OAuthUserInfo googleUser = new GoogleUser((Map<String, Object>) data.get("profileObj"));
        System.out.println(googleUser.getName());
        String jwt = userService.oauthUserSave(googleUser);

        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return ResponseEntity.ok()
                .headers(headers)
                .body(new CommonResponse<>(new TokenDto(jwt)));
    }

    //일반 회원가입 요청
    @PostMapping("/api/v1/signup")
    public ResponseEntity<? extends BasicResponse> joinUser(@RequestBody UserDto.UserSaveRequestDto userSaveRequestDto) throws MessagingException {
        Long result = userService.userSave(userSaveRequestDto);
        if (result != 0) {
            return ResponseEntity.created(URI.create("/api/v1/user/" + result)).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("중복 유저네임 가입 요청"));
    }


}