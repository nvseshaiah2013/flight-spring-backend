package com.cg.flight.security;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.cg.flight.filter.JwtFilter;
import com.cg.flight.services.LoginUserService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter  {
	@Autowired
	private LoginUserService loginUserService;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	
	  @Override protected void configure(AuthenticationManagerBuilder auth) throws
	  Exception { 
		  auth.userDetailsService(loginUserService);
	  
	 }
	 @Override 
	 protected void configure(HttpSecurity http) throws Exception
	 {
		 
		 http.csrf().disable().	 
		 authorizeRequests().antMatchers("/users/authenticate","/users/add")
		 .permitAll()
		 .anyRequest()
		 .authenticated()
		 .and()
		 .sessionManagement()
		 .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		 http.cors();
		 http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		 
	 }
	 
	 @Override
		@Bean
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}
	 
	  @Bean
	  public PasswordEncoder passwordEncoder()
	  {
//		  return NoOpPasswordEncoder.getInstance();
		  return new BCryptPasswordEncoder();
	  }
	 
	
}
