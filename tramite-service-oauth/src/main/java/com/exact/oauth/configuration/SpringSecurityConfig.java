package com.exact.oauth.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	//busca un servicio con  userdetailservice
		@Autowired
		private UserDetailsService usuarioservice;
		
		
		
		//para encryptar la contraseña
		@Bean
		public BCryptPasswordEncoder passwordEndocer() {
			return new BCryptPasswordEncoder();
		}
		
		
		
		//se debe registrar el usuario detailservice para registrar en authenticationmanager builder
		@Override
		@Autowired
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(this.usuarioservice).passwordEncoder(passwordEndocer());
		}

		@Override
		@Bean
		protected AuthenticationManager authenticationManager() throws Exception {
			return super.authenticationManager();
		}
}
