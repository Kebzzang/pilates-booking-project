 package com.keb.club_pila.config.jwt;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class CookieUtil {
    @Value("${jwt.token-validity}")
    private Long validity;
    //쿠키생성해주기
    //쿠키 이름과 값 설정
    //httpOnly 설정, 유효시간 등 설정
    public Cookie generateCookie(String cookieName, String value) {
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(Math.toIntExact(validity));
        cookie.setSecure(false);

        cookie.setPath("/");

        return cookie;
    }
    //리퀘스트가 들어올 때마다 내가 설정한 이름의 쿠키를 받아준다. 쿠키없이 들어오면 널 반환
    public Cookie getCookie(HttpServletRequest request, String cookieName){
        final Cookie[] cookies=request.getCookies();
        if(cookies==null) return null;

        for(Cookie cookie: cookies){
            if(cookie.getName().equals(cookieName)){
                return cookie;
            }
        }
        return null;

    }
}
