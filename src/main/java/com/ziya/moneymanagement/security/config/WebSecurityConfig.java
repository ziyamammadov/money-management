package com.ziya.moneymanagement.security.config;

import com.ziya.moneymanagement.security.JwtAccessDeniedHandler;
import com.ziya.moneymanagement.security.JwtAuthenticationEntryPoint;
import com.ziya.moneymanagement.security.jwt.JWTConfigurer;
import com.ziya.moneymanagement.security.jwt.TokenProvider;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

   private final TokenProvider tokenProvider;
   private final CorsFilter corsFilter;
   private final JwtAuthenticationEntryPoint authenticationErrorHandler;
   private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

   public WebSecurityConfig(
           TokenProvider tokenProvider,
           CorsFilter corsFilter,
           JwtAuthenticationEntryPoint authenticationErrorHandler,
           JwtAccessDeniedHandler jwtAccessDeniedHandler
   ) {
      this.tokenProvider = tokenProvider;
      this.corsFilter = corsFilter;
      this.authenticationErrorHandler = authenticationErrorHandler;
      this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
   }

   // Configure paths and requests that should be ignored by Spring Security

   @Override
   public void configure(WebSecurity web) {
      web.ignoring()
              .antMatchers(HttpMethod.OPTIONS, "/**")

              // allow anonymous resource requests
              .antMatchers(
                      "/actuator/*",
                      "/",
                      "/*.html",
                      "/favicon.ico",
                      "/**/*.html",
                      "/**/*.css",
                      "/**/*.js",
                      "/h2-console/**"
              );
   }

   // Configure security settings

   @Override
   protected void configure(HttpSecurity httpSecurity) throws Exception {
      httpSecurity
              // we don't need CSRF because our token is invulnerable
              .csrf().disable()

              .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

              .exceptionHandling()
              .authenticationEntryPoint(authenticationErrorHandler)
              .accessDeniedHandler(jwtAccessDeniedHandler)

              // enable h2-console
              .and()
              .headers()
              .frameOptions()
              .sameOrigin()

              // create no session
              .and()
              .sessionManagement()
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

              .and()
              .authorizeRequests()
              .antMatchers("/user/**").permitAll()
              .antMatchers("/payment/**").hasAuthority("ROLE_USER")
              .antMatchers("/transaction/**").hasAuthority("ROLE_ADMIN")
              .anyRequest().authenticated()

              .and()
              .apply(securityConfigurerAdapter());
   }

   private JWTConfigurer securityConfigurerAdapter() {
      return new JWTConfigurer(tokenProvider);
   }
}