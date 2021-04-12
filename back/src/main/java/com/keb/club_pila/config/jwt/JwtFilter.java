package com.keb.club_pila.config.jwt;

import com.keb.club_pila.config.CustomUserDetails;
import com.keb.club_pila.config.CustomUserDetailsService;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;


@Component
public class JwtFilter extends GenericFilterBean {
    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtProvider jwtProvider;

    public JwtFilter(JwtProvider jwtProvider, CustomUserDetailsService customUserDetailsService) {
        this.jwtProvider = jwtProvider;
        this.customUserDetailsService=customUserDetailsService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.debug("do filter...");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = resolveToken(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();
        System.out.println("jwt"+jwt+"\nrequestURI::"+requestURI);
        if(hasText(jwt)&& jwtProvider.validateToken(jwt)){
            String username= jwtProvider.getUsernameFromToken(jwt);
            CustomUserDetails customUserDetails=customUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        else{
            logger.debug("유효한 JWT 토큰이 없음, uri: {}", requestURI);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); //앞에 베어러를 제거하고 리턴함
        }
        return null;
    }

}
