package com.keb.club_pila.config.jwt;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
@Component
//spring은 필터에서 spring config 설정 정보를 쉽게 처리하기 위한 GenericFilterBean을 제공한다.
//필터가 중첩 호출한 경우 매번 필터의 내용이 중첩 수행되는 것을 방지하기 위해 GenericFilterBean을 상속한 OncePerRequestFilter도 있음

public class JwtFilter extends GenericFilterBean {
 //   private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final JwtProvider jwtProvider;
    private final CookieUtil cookieUtil;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //모든 리퀘스트는 여기를 거쳐간다. 토큰 가지고 왔니? 체크
        //가지고 왔다면 우리 컨텍스트에 이 토큰이 유효한 것인지 확인
        System.out.println("*****JWT Filter Comes up*****");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        //jwt토큰 초기화
        String jwt = null;
        Cookie cookie = cookieUtil.getCookie(httpServletRequest, "accessToken");
        //쿠키에 토큰 정보를 들고 온다면 => 이미 로그인한 사람이니까
       if (cookie != null && jwtProvider.validateToken(cookie.getValue())) {
            jwt = cookie.getValue();

            Authentication authentication = jwtProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        //필터 걸어주기
        filterChain.doFilter(servletRequest, servletResponse);

       /* logger.debug("do filter...");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = resolveToken(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();
        System.out.println("jwt" + jwt + "\nrequestURI::" + requestURI);
        if (hasText(jwt) && jwtProvider.validateToken(jwt)) {
            String username = jwtProvider.getUsernameFromToken(jwt);
            CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(customUserDetails, "", customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            logger.debug("유효한 JWT 토큰이 없음, uri: {}", requestURI);
        }*/

        // HttpServletResponse response = (HttpServletResponse) servletResponse;
//        boolean a=httpServletRequest.authenticate(response);
//        System.out.println(":::"+a);
//        if (httpServletRequest.isUserInRole("ADMIN"))
//        {System.out.println("Hello admin");}
//
//        else if (httpServletRequest.isUserInRole("ROLE_ADMIN"))
//        {System.out.println("hello admin2");}
//        else {
//            System.out.println("hello admin3");
//        }
    }

//    private String resolveToken(HttpServletRequest request) {
//        System.out.println("here1");
//
//
//        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
//        if (hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7); //앞에 베어러를 제거하고 리턴함
//        }
//        return null;
//    }

}
