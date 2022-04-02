/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hal.configs;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.hal.configs.handlers.LoginSuccessHandler;
import com.hal.configs.handlers.LogoutHandler;
import com.hal.configs.handlers.MyAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author Asus
 */
@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan(basePackages = {
    "com.hal.repository",
    "com.hal.service"
})
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationSuccessHandler loginSuccessHandler;
    @Autowired
    private LogoutSuccessHandler logoutHandler;
    @Autowired
    private MyAccessDeniedHandler accessDenied;
   
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public Cloudinary cloudinary() {
        Cloudinary c = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "ddphodfop    ",
            "api_key", "639657923925892",
            "api_secret", "qUhE-fvxlzYeATpO5EtR5v6lG8M",
            "secure", true
        ));
        
        return c;
    }
    
    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }
    
    @Bean
    public LogoutSuccessHandler logoutHandler() {
        return new LogoutHandler();
    }
    
    @Bean
    public MyAccessDeniedHandler accessDenied() {
        return new MyAccessDeniedHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password");
        
        http.formLogin().defaultSuccessUrl("/").failureUrl("/login?error");
        http.formLogin().successHandler(this.loginSuccessHandler);
        
//        http.logout().logoutSuccessUrl("/login");
        http.logout().logoutSuccessHandler(this.logoutHandler);
        
        http.exceptionHandling().accessDeniedPage("/login?accessDenied");
//        http.exceptionHandling().accessDeniedHandler(accessDenied);
//        http.exceptionHandling().
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')");
        
        http.csrf().disable();
    }
    
    
    
}
