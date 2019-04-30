package com.me.apartment_management_web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class LoginConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/login").permitAll()  // 允许任何人访问登录页面
                .anyRequest().authenticated()  //  允许任何通过认证的用户访问任何路径
                .and()
            .formLogin()
                .defaultSuccessUrl("/");  // 登录成功后默认访问的路径
    }

    // 密码加密方式
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // 重写方法，自定义用户
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 内存中添加三个用户
        auth.inMemoryAuthentication().withUser("wanger").password(new BCryptPasswordEncoder().encode("wanger")).roles("USER");
        auth.inMemoryAuthentication().withUser("zhangsan").password(new BCryptPasswordEncoder().encode("zhangsan")).roles("USER");
        auth.inMemoryAuthentication().withUser("lisi").password(new BCryptPasswordEncoder().encode("lisi")).roles("USER");
    }

}
