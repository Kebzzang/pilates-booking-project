package com.keb.club_pila.config.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static String getCurrentMemberUsername(){
        final Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Auth:::"+authentication.getAuthorities()+":::"+authentication.getPrincipal());

        if(authentication==null || authentication.getName()==null){
            throw new RuntimeException("Security Context 에 인증 정보가 없습니다.");
        }
        return authentication.getName();
    }
}
