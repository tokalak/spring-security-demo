package com.tklk;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
class SecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(final HttpSecurity httpSec) throws Exception {
    return httpSec
        .authorizeHttpRequests(
            authorizeHttp -> {
              // Recommended pattern to follow:
              //   1. List the accessible resource explicitly
              //   2. Block non-authenticated access to all other resources

              authorizeHttp.requestMatchers("/").permitAll();
              authorizeHttp.requestMatchers("/css/*").permitAll();
              authorizeHttp.requestMatchers("/error").permitAll();
              authorizeHttp.anyRequest().authenticated(); // block all other resources
            }
        )
        .formLogin(l -> l.defaultSuccessUrl("/secured"))
        .logout(l -> l.logoutSuccessUrl("/"))
        .httpBasic(withDefaults())
        .addFilterBefore(new SloganFilter(), LogoutFilter.class) // place filter before logout
        .authenticationProvider(new AdminAuthProvider()) // our custom AuthenticationProvider
        .build();
  }


}
