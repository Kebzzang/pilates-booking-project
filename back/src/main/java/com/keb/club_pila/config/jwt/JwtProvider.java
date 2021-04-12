package com.keb.club_pila.config.jwt;

import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider /*implements InitializingBean */{
    private final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    private final String secret;
    private final long tokenValidityInMilliseconds;
   // private Key key;
    public JwtProvider(@Value("${jwt.secret}") String secret, @Value("${jwt.token-validity}") long tokenValidityInSeconds) {
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds*1000;
    }
//    @Override
//    public void afterPropertiesSet() { //디코드해서 키넣어주기!!
//        byte[] keyBytes = Decoders.BASE64.decode(secret);
//        this.key = Keys.hmacShaKeyFor(keyBytes);
//
//    }

    public String generateToken(String username){
        long now=(new Date()).getTime();
    Date validity=new Date(now+this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public boolean validateToken(String token){
        try{
        Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        return true;
    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
        logger.info("잘못된 JWT 서명입니다.");
    } catch (ExpiredJwtException e) {
        logger.info("만료된 JWT 토큰입니다.");
    } catch (UnsupportedJwtException e) {
        logger.info("지원되지 않는 JWT 토큰입니다.");
    } catch (IllegalArgumentException e) {
        logger.info("JWT 토큰이 잘못되었습니다.");
    }
        return false;
    }

    public String getUsernameFromToken(String token){
        Claims claims=Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
