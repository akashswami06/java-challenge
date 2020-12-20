package jp.co.axa.apidemo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	public SpringSecurityConfig() {

	}
	
	 @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

	        auth.inMemoryAuthentication()
	                .withUser("user").password("{noop}password").roles("USER")
	                .and()
	                .withUser("admin").password("{noop}password").roles("USER", "ADMIN");

	    }
	 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http
         //HTTP Basic authentication
         .httpBasic()
         .and()
         .authorizeRequests()
         .antMatchers(HttpMethod.GET, "/api/v1/employees/**").hasRole("USER")
         .antMatchers(HttpMethod.GET, "/api/v1/employees").hasRole("USER")
         .antMatchers(HttpMethod.POST, "/api/v1/employees").hasRole("ADMIN")
         .antMatchers(HttpMethod.PUT, "/api/v1/employees/**").hasRole("ADMIN")
         .antMatchers(HttpMethod.DELETE, "/api/v1/employees/**").hasRole("ADMIN")
         .and()
         .csrf().disable()
         .formLogin().disable();
	}
	/*
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}*/

}