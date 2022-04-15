/**
 * 
 */
package testapp;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;


@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
   
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
     // @formatter:off
        System.out.println("########### FILTER CHAIN HTTP SECURTITY ########## " + http.toString());
        http.requestMatchers(matchers -> matchers.antMatchers(
              "/secured/**"))
            .authorizeHttpRequests(authorize -> authorize.anyRequest().hasAuthority("ROLE_USER"))
            .addFilterBefore(myFilter(), BasicAuthenticationFilter.class);
                   
     // @formatter:on
        return http.build();
    }

    @Bean
    public Filter myFilter() {
        return new OncePerRequestFilter() {
            
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                    FilterChain filterChain)
                    throws ServletException, IOException {
                System.out.println("####### My custom java configured filter #####");
                response.setStatus(200);
                response.getWriter().write("Custom Java configured filter reached");
                
            }
        };
    }
}
