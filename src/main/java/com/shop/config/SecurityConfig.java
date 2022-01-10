package com.shop.config;

import com.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MemberService memberService;

    @Override
    protected void configure(HttpSecurity http) throws Exception { //Http요청에 대한 보안 설정(페이지 권한 설정)
        http.formLogin()
                .loginPage("/members/login") //로그인 페이지 URL 설정
                .defaultSuccessUrl("/") //로그인 성공시 이동할 URL
                .usernameParameter("email") //로그인 시 사용할 파라미터 이름 지정
                .failureUrl("/members/login/error") //로그인 실패 시 이동할 URL
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) //로그아웃 URL 설정
                .logoutSuccessUrl("/"); //로그아웃 성공 시 이동할 URL 설정
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Spring Security에서 인증은 AuthenticationManager를 통해 이루어짐
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){ //해시 함수로 비밀번호 암호화
        return new BCryptPasswordEncoder();
    }
}
