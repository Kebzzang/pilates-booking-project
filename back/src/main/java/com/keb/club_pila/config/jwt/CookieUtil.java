package com.keb.club_pila.config.jwt;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class CookieUtil {
    @Value("${jwt.token-validity}")
    private Long validity;

    public Cookie generateCookie(String cookieName, String value) {
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(Math.toIntExact(validity));
        cookie.setSecure(true);
        cookie.setPath("/");

        return cookie;
    }

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
