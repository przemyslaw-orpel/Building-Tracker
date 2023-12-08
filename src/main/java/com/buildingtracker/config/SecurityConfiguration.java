package com.buildingtracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeHttpRequests(request -> request
                        .requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll()
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/authorization")
                        .defaultSuccessUrl("/?building=X1&level=2", true)
                        .permitAll())
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID");
        return httpSecurity.build();
    }
}
