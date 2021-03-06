package com.keb.club_pila.config;


import com.keb.club_pila.config.jwt.JwtFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;

@Configuration
public class CorsConfig {
    private static final Logger logger = LoggerFactory.getLogger(CorsConfig.class);
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        List<String> allowOrigins = Arrays.asList(
//스카 로컬호스트
                "http://210.99.231.107:3000/**",
                "http://210.99.231.107:3000",
                "http://210.99.231.107:3000/",
                "http://211.221.181.6/**", "http://211.221.181.6:3000/",
                "http://3.38.35.210:80/**","http://3.38.35.210:80","http://3.38.35.210/**","http://3.38.35.210",
                "http://localhost:80/**", "http://localhost:80","http://localhost:3000", "http://localhost:3000/**");
        config.setAllowedOriginPatterns(allowOrigins);
        config.setAllowedMethods(singletonList("*"));
        config.setAllowedHeaders(singletonList("*"));
        config.addExposedHeader("location");
        logger.info("hello new corsfilter here");
        source.registerCorsConfiguration("/api/v1/**", config);
        return new CorsFilter(source);
    }
}
