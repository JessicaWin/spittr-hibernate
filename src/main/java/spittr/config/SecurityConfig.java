package spittr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import spittr.dao.SpitterRepository;
import spittr.service.SpitterUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SpitterRepository spitterRepository;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(new SpitterUserService(spitterRepository));
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().loginPage("/login").and().httpBasic().realmName("Spittr").and().rememberMe()
				.tokenValiditySeconds(2419200).key("spittrKey").and().logout().logoutSuccessUrl("/").and()
				.authorizeRequests().antMatchers("/spitters/me").authenticated()
				.antMatchers(HttpMethod.POST, "/spittles").authenticated().anyRequest().permitAll().and()
				.requiresChannel().antMatchers("/spitter/form").requiresSecure().antMatchers("/").requiresInsecure();
	}

}
