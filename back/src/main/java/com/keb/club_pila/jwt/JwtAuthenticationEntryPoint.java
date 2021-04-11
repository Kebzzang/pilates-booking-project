package com.keb.club_pila.jwt;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    //유효한 자격증명 없이 접근할 떄 에러 리턴
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        System.out.println("entryPoint");
        String bearerToken = httpServletRequest.getHeader("Authorization");
        System.out.println("bearerToken:::"+bearerToken);
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
