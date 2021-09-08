package com.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.demo.admin.enumeration.AdminRole;
import com.demo.admin.service.AdminUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final AdminUserService adminUserService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public SecurityConfig(AdminUserService adminUserService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.adminUserService = adminUserService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.cors()
		.and()
		.authorizeRequests()
		.antMatchers("/", "/addToCart/**", "/css/**", "admin/add")
		.permitAll()
		.antMatchers("/admin").hasRole(AdminRole.SUPERADMIN.name())
		.anyRequest()
		.authenticated()
		.and()
		.formLogin();
	}
	
	
	
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails superAdmin = User.builder()
				.username("admin")
				.password(bCryptPasswordEncoder.encode("password"))
				.roles(AdminRole.SUPERADMIN.name())
				.build();
		
		return new InMemoryUserDetailsManager(
					superAdmin
				);
	}



	@Override 
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}



	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
		dao.setPasswordEncoder(bCryptPasswordEncoder);
		dao.setUserDetailsService(userDetailsService());
		return dao;
	}
	
}
