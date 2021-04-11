package com.keb.club_pila.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {
    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private TokenProvider tokenProvider;

    public JwtFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    //실제 필터링 내용 여기서부터
    //토큰의 인증정보를 SecurityContext에 저장

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse=(HttpServletResponse) servletResponse;

        String jwt = resolveToken(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();
        String pathinfo=httpServletRequest.getPathInfo();

        System.out.println("jwt"+jwt+"\nrequestURI::"+requestURI+"\nPathinfo::"+pathinfo);
        //유효성 검증하고 정상 토큰이면 시큐리티컨텍스트에 저장(set)
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            System.out.println("No problem1");

            Authentication authentication = tokenProvider.getAuthentication(jwt);
            System.out.println("::::"+authentication.getPrincipal().toString());SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("Security Context here: "+authentication.getAuthorities()+":::"+requestURI);
            logger.debug("Security context에 '{}' 인증 정보를 저장함, uri: {}", authentication.getName(), requestURI);
        }
        else{
            logger.debug("유효한 JWT 토큰이 없음, uri: {}", requestURI);
        }
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("no problem 2");
    }

    //리퀘스트 헤더에서 토큰 정보를 꺼내오기 위한 메소드 추가
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); //앞에 베어러를 제거하고 리턴함
        }
        return null;
    }
}
