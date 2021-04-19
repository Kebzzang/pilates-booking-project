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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
@Component
public class JwtFilter extends GenericFilterBean {
    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final JwtProvider jwtProvider;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
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
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = resolveToken(httpServletRequest);



        if (hasText(jwt) && jwtProvider.validateToken(jwt)) {
            Authentication authentication = jwtProvider.getAuthentication(jwt);
             SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("here JwtFilter  :::: "+authentication);

          }

        filterChain.doFilter(servletRequest, servletResponse);
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

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); //앞에 베어러를 제거하고 리턴함
        }
        return null;
    }

}
