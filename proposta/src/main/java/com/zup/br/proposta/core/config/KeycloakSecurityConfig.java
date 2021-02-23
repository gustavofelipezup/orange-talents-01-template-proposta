package com.zup.br.proposta.core.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

@Configuration
@EnableWebSecurity
public class KeycloakSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure (HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/propostas/**").hasAnyAuthority("SCOPE_write")
		.antMatchers("/h2-console/**").permitAll()
		.anyRequest().authenticated()
		.and()
		.csrf().disable()
        .headers().frameOptions().disable()
		.and().oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
	}
}
