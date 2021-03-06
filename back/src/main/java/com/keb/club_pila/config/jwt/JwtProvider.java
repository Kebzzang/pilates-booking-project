package com.keb.club_pila.config.jwt;

import com.keb.club_pila.config.Custom.CustomUserDetails;
import com.keb.club_pila.model.entity.user.Member;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtProvider implements InitializingBean {
    private final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    private final String secret;
    private static final String AUTHORITIES_KEY = "Role";
    private final long tokenValidityInMilliseconds;
    private Key key;

    public JwtProvider(@Value("${jwt.secret}") String secret, @Value("${jwt.token-validity}") long tokenValidityInSeconds) {
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
    public String generateTokenforOAuth(Member user){
        long now = (new Date()).getTime();
        System.out.println(user.getUsername()+"generateTokenForOAuth");
        Date validity = new Date(now + this.tokenValidityInMilliseconds);
        return Jwts.builder()
                .setSubject(user.getUsername()) //authentication.getName();
                .claim(AUTHORITIES_KEY, "ROLE_USER")  //???????????? role
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateToken(Authentication authentication) { //Authentication authentication?????? ?????????
        long now = (new Date()).getTime();
        String authorities = authentication.getAuthorities().stream() //?????? ??????????????? 
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        Date validity = new Date(now + this.tokenValidityInMilliseconds);
     //   String authority=authentication.getAuthorities().toString();
        return Jwts.builder()
                .setSubject(authentication.getName()) //authentication.getName();
                .claim(AUTHORITIES_KEY, authorities)  //???????????? role
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("JWT"+token);
            logger.info("????????? JWT ???????????????.");
        } catch (ExpiredJwtException e) {
            logger.info("????????? JWT ???????????????.");
        } catch (UnsupportedJwtException e) {
            logger.info("???????????? ?????? JWT ???????????????.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT ????????? ?????????????????????.");
        }
        return false;
    }


    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        System.out.println(accessToken+" token info\n"+claims);
        if(claims.get(AUTHORITIES_KEY)==null){
            throw new RuntimeException("?????? ????????? ?????? ???????????????.");
        }
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        System.out.println("claim here jwt get"+claims.getSubject());
        CustomUserDetails principal = new CustomUserDetails(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }



}
