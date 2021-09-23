package com.keb.club_pila.config;

import com.keb.club_pila.config.jwt.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtProvider jwtProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CookieUtil cookieUtil;
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .httpBasic().disable()
                .csrf().disable()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling()
                //
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                //요구되는 권한 없이 접근하려 들 때 403 에러
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                //어드민 롤만 가능한 api들
                .antMatchers("/api/v1/admin/**").hasRole("ADMIN")
                //인증 정보 없이 api 요청 가능함: 로그인한 정보 요청, 로그인아웃, 그 외 등등
                .antMatchers("/api/v1/user/me", "/api/v1/logout","/api/v1/signup","/api/v1/authadmin", "/api/v1/auth", "/api/v1/user/email/certified", "/api/v1/oauth/google"
    ).permitAll()
                //그 외는 어드민이든 티처이든 유저이든 인증되면 리퀘스트할 수 있다.
                .anyRequest().authenticated()

                .and()
                .apply(new JwtSecurityFilter(jwtProvider, cookieUtil));

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
