package com.codex.ecam.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.codex.ecam.dao.FocusTokenRepository;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	@Qualifier("customRequestCache")
	RequestCache customRequestCache;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/resetPassword/**").permitAll()
		.antMatchers("/resources/**").permitAll()
		.antMatchers("/uploads/**").permitAll()
		.antMatchers("/restapi/**").permitAll()
		.antMatchers("/validate/**").permitAll()
		.antMatchers("/session-expire-redirect").permitAll()
		.antMatchers("/login").permitAll()
		.antMatchers("/**").authenticated()
		.and()
		.csrf().ignoringAntMatchers("/restapi/web/**")
		.and()
		.formLogin()
		.loginPage("/login")
		.loginProcessingUrl("/login")
		.defaultSuccessUrl("/", true)
		.failureUrl("/login?invalid")
		.usernameParameter("username")
		.passwordParameter("password")
		.and()
		.rememberMe().rememberMeParameter("remember").tokenRepository(persistentTokenRepository())
		.tokenValiditySeconds(1209600)// 14 days valid(1 days-86400,30 days-2628000,1 year-31536000)
		.and()
		.logout().invalidateHttpSession(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/login?logout")
		.and()
		.requestCache()
		.requestCache(customRequestCache)
		.and()
		.exceptionHandling().accessDeniedPage("/access-denied").authenticationEntryPoint(authenticationEntryPoint())
		.and()
		.sessionManagement()
		.invalidSessionUrl("/login?invalid");
	}

	private AuthenticationEntryPoint authenticationEntryPoint() {
		return (request, response, authException) -> response.sendRedirect(request.getContextPath() + "/login?invalid");
	}

	@Bean
	public AuthenticationTrustResolver authenticationTrustResolver() {
		return new AuthenticationTrustResolverImpl();
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		FocusTokenRepository tokenRepositoryImpl = new FocusTokenRepository();
		tokenRepositoryImpl.setDataSource(dataSource);
		return tokenRepositoryImpl;
	}
}
