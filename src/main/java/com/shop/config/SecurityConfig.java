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

        http.authorizeRequests()
                .mvcMatchers("/", "/members/**", "/item/**", "/images/**").permitAll() //해당 경로에 모든 사용자가 인증없이 접근 가능
                .mvcMatchers("/admin/**").hasRole("ADMIN") //admin으로 시작하는 경로는 ADMIN Role인 경우에만 접근 가능
                .anyRequest().authenticated(); //위에서 설정해준 경로를 제외한 나머지 경로들은 모두 인증을 요구하도록 설정

        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()); //인증되지 않은 사용자가 접근시 수행되는 핸들러 등록
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Spring Security에서 인증은 AuthenticationManager를 통해 이루어짐
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**"); //static 디렉터리 하위 파일은 인증을 무시하도록 설정
    }

    @Bean
    public PasswordEncoder passwordEncoder(){ //해시 함수로 비밀번호 암호화
        return new BCryptPasswordEncoder();
    }
}
