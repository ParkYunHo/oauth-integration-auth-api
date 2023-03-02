package com.john.res.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.header.writers.frameoptions.WhiteListedAllowFromStrategy
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter

/**
 * @author yoonho
 * @since 2023.03.01
 */
@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun configure(http: HttpSecurity): SecurityFilterChain =
        http
//            .formLogin().disable()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .httpBasic().disable()
            .csrf().disable()
            .authorizeHttpRequests()
//                .anyRequest().permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/resources/**").permitAll()
                .requestMatchers("/static/**").permitAll()
                .requestMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
            // H2 Console 허용
            .headers()
                .addHeaderWriter(XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                .and()
            .build()
}