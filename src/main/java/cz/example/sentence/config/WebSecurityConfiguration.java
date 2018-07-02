package cz.example.sentence.config;

import cz.example.sentence.rest.UserController;
import org.h2.server.web.WebServlet;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableOAuth2Sso
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf()
				.disable()
			.antMatcher("/**")
			.authorizeRequests()
			.antMatchers("/", "/console**", "/index.html", "/login**", UserController.MAPPING_BASE + "/simpleUser", "/webjars/**", "/error**")
				.permitAll()
			.anyRequest()
				.authenticated()
			.and().logout().logoutSuccessUrl("/")
				.permitAll()
			.and().headers().frameOptions().sameOrigin();
	}

	@Bean
	ServletRegistrationBean h2ServletRegistration() {
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
		registrationBean.addUrlMappings("/console/*");
		return registrationBean;
	}
}
