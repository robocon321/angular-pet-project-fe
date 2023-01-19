package com.robocon321.demo.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.robocon321.demo.security.AuthEntryPointJwt;
import com.robocon321.demo.security.AuthTokenFilter;
import com.robocon321.demo.util.JwtUtils;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Autowired
	public AuthTokenFilter authTokenFilter;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
		return authConfiguration.getAuthenticationManager();
	}
	
	@Autowired
	private AuthEntryPointJwt authEntryPointJwt;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		CorsConfiguration config = new CorsConfiguration();

		config.setAllowCredentials(true);
		config.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type", "Access-Control-Allow-Origin"));
		config.addAllowedMethod("*");
		config.setAllowedOrigins(List.of("http://localhost:4200"));
		
		String[] whitelist = {
//				"/api/blog/**",
		        "/authenticate",
		        "/swagger-resources/**",
		        "/swagger-ui.html",
		        "/swagger-ui/**",
		        "/api-docs/**",
		        "/webjars/**"
		};
		
		http.cors().configurationSource(request -> config).and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(authEntryPointJwt)
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().authorizeHttpRequests().requestMatchers(whitelist).permitAll()
			.anyRequest().authenticated();
		http.headers().frameOptions().disable();
		http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
