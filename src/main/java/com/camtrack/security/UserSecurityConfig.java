// Decompiled by Procyon v0.5.30
//

package com.camtrack.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter.ReferrerPolicy;
import org.springframework.web.client.RestTemplate;

import com.camtrack.config.ConValue;

//import springfox.documentation.swagger.web.SecurityConfiguration;

@Configuration
//@Import({ SecurityConfiguration.class })
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {
	public static final String DEF_AUTHORITIES_BY_USERNAME_QUERY = "select username,authority from authorities where username = ?";
	public static final String DEF_GROUP_AUTHORITIES_BY_USERNAME_QUERY = "select g.id, g.group_name, ga.authority from \"groups\" g, group_members gm, group_authorities ga where gm.username = ? and g.id = ga.group_id and g.id = gm.group_id";
	public static final String DEF_USERS_BY_USERNAME_QUERY = "select username,password,enabled from users where username = ?";
	@Autowired
	DataSource ds;
	@Value("${enable.swagger.plugin}")
	public boolean enableSwaggerPlugin;

	@Autowired
	private CustomLoginFailureHandler loginFailureHandler;

	@Autowired
	private CustomLoginSuccessHandler loginSuccessHandler;

	/**
	 * @Bean({ "org.springframework.security.userDetailsService" }) public
	 * UserDetailsService userDetailsServiceBean() throws Exception { //return
	 * super.userDetailsServiceBean(); return userService; }
	 */

	@Override
	@Bean(name = { "org.springframework.security.authenticationManager" })
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		final JdbcUserDetailsManagerConfigurer<AuthenticationManagerBuilder> cfg = ((JdbcUserDetailsManagerConfigurer) auth
				.jdbcAuthentication().passwordEncoder(this.userPasswordEncoder())).dataSource(this.ds)
						.usersByUsernameQuery("select username,password,enabled from users where username = ?")
						.authoritiesByUsernameQuery("select username,authority from authorities where username = ?")
						.groupAuthoritiesByUsername(
								"select g.id, g.group_name, ga.authority from \"groups\" g, group_members gm, group_authorities ga where gm.username = ? and g.id = ga.group_id and g.id = gm.group_id");
		cfg.getUserDetailsService().setEnableGroups(true);
		cfg.getUserDetailsService().setEnableAuthorities(false);

	}

	@Override
	@Order
	public void configure(final HttpSecurity http) throws Exception {
		((HttpSecurity) ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl) ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl) ((HttpSecurity) ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl) ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl) http
				//.cors().and()
				.csrf().disable().authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS, new String[] { "*" })).permitAll().antMatchers(
						new String[] { "/oauth/token" })).permitAll().and()).formLogin().and().httpBasic().and()
								.authorizeRequests().antMatchers(new String[] { "/**", "/img/**", "/noauths/**",
										"/api-docs/**", "/v3/api-docs", "/v3/api-docs/**", "/swagger-ui.html",
										"/swagger-ui/**" })).permitAll().anyRequest()).authenticated().and())
												.httpBasic().and().csrf().disable().headers().cacheControl()
												.disable().xssProtection().and()
												.contentSecurityPolicy(
														"script-src 'self';object-src 'self';form-action 'self'")
												.and().referrerPolicy(ReferrerPolicy.NO_REFERRER);
		;
	}

	/**
	 * public void configure(final WebSecurity web) throws Exception {
	 * ((WebSecurity.IgnoredRequestConfigurer)
	 * ((WebSecurity.IgnoredRequestConfigurer) web.ignoring() .antMatchers(new
	 * String[] { "/img", "/img/**", "/noauths", "/noauths/**", "/v2/api-docs",
	 * "/configuration/**", "/swagger-resources/**", "/swagger-ui.html",
	 * "/webjars/**", "/api-docs/**" })).antMatchers(HttpMethod.GET, new String[] {
	 * "/actuator/**" })) .antMatchers(HttpMethod.OPTIONS, new String[] { "/**" });
	 * }
	 */

	@Override
	public void configure(final WebSecurity web) throws Exception {
		(web.ignoring()
				.antMatchers(new String[] { "/img", "/img/**", "/noauths", "/noauths/**", "/api-docs/**",
						"/v3/api-docs", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**" }))
								.antMatchers(HttpMethod.OPTIONS, new String[] { "/**" });
	}

	@Bean
	public RestTemplate restTemplate() {
		final RestTemplate restTemplate = new RestTemplate();
		((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setConnectTimeout(0);
		((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setReadTimeout(0);
		return restTemplate;
	}

	@Bean({ "userPasswordEncoder" })
	PasswordEncoder userPasswordEncoder() {
		return new BCryptPasswordEncoder(ConValue.getLengthbcryt());
	}
}
