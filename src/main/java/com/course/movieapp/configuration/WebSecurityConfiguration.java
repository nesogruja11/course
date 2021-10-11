package com.course.movieapp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.course.movieapp.exception.CustomAccessDeniedHandler;
import com.course.movieapp.exception.SimpleAuthenticationEntryPoint;

@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final String[] ADMIN_URLS = { "/movie-role/**", "/movie-people/**", "/language/**",
			"/content-type/**", "/country/**", "/genre/**", "/content/**" };

	private static final String[] USER_URLS = { "/user/rating", "/user/update", "/user/favour", "/user/comment",
			"/user/edit-comment", "/user/delete-comment", "/user/favourite-movies", "/user/favourite-series" };

	private static final String[] PERMIT_ALL_URLS = { "/user/register", "/user/login", "/user/forgot-password",
			"/user/reset-password",
			// -- Swagger UI v2
			"/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
			"/configuration/security", "/swagger-ui.html", "/webjars/**",
			// -- Swagger UI v3 (OpenAPI)
			"/v3/api-docs/**", "/swagger-ui/**", "/actuator/**" };

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	JwtRequestFilter jwtRequestFilter;

	@Autowired
	SimpleAuthenticationEntryPoint simpleAuthenticationEntryPoint;

	@Autowired
	CustomAccessDeniedHandler customAcessDeniedHandler;

	// authentication
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
	}

	// authorization
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off

            http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(PERMIT_ALL_URLS).permitAll()
                .antMatchers(ADMIN_URLS).hasRole("ADMIN")
                .antMatchers(USER_URLS).hasAnyRole("USER")
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()				
				.exceptionHandling().authenticationEntryPoint(simpleAuthenticationEntryPoint)
				.accessDeniedHandler(customAcessDeniedHandler);
            http
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        // @formatter:on
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(11);
	}
}
