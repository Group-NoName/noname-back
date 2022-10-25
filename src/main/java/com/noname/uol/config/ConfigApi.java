package com.noname.uol.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.noname.uol.config.jwt.token.TokenFilter;
import com.noname.uol.config.jwt.token.TokenService;
import com.noname.uol.servicos.UserDetaisService;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ConfigApi extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private TokenService tokenService;

	@Autowired
	private UserDetaisService userDetaisService;
	
	private static final String[] rotasPublicas = { 
			"/produto/produtos",
			"/pacote/pacotes",
			"/tag/tags/",
			"/pacote/pacotes",
			"/oferta/ofertas",
			"/categoria/categorias/",
			"/swagger-ui/**",
			"/v2/api-docs/**",
			"/swagger-resources/**",
			"/swagger-ui.html",
			"/webjars/**",
			"/swagger.json",
			"/user/cadastro",
			"/user/login"
	};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		http.cors().and().csrf().disable();

		http.authorizeHttpRequests().antMatchers(rotasPublicas).permitAll().anyRequest().authenticated().and().addFilterAfter(
				new TokenFilter(authenticationManager(), tokenService, userDetaisService), UsernamePasswordAuthenticationFilter.class);

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder autenticador) throws Exception{
		autenticador.userDetailsService(userDetaisService).passwordEncoder(new BCryptPasswordEncoder());
	}

	
	  @Bean
	  CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
	    configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	  }
	  
		@Override
		@Bean
		protected AuthenticationManager authenticationManager() throws Exception {
			return super.authenticationManager();
		}
}
