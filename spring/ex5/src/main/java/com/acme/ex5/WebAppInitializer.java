package com.acme.ex5;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.acme.ex3.ApplicationConfig;
import com.acme.ex5.formatter.LocalDateFormatter;
import com.acme.ex5.formatter.StringFormatter;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Configuration @EnableWebSecurity
	public static class SecurityConfig extends WebSecurityConfigurerAdapter{
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.inMemoryAuthentication().withUser("jdoe").password("azerty").authorities("fbc");
		}
		@Override
		protected void configure(HttpSecurity http) throws Exception {
		    http
		        .authorizeRequests().anyRequest().authenticated()
		        .and()
		            .formLogin().loginPage("/login").permitAll()
		        .and()
		            .logout().logoutUrl("/logout").logoutSuccessUrl("/logout-success").permitAll();
		}		
	}

	@Configuration @EnableWebMvc @ComponentScan
	public static class MvcConfiguration extends WebMvcConfigurerAdapter {
				
		@Override
		public void configureViewResolvers(ViewResolverRegistry registry) {
			registry.jsp().prefix("/WEB-INF/views/").suffix(".jsp");
		}
		
		@Override
		public void addFormatters(FormatterRegistry registry) {
			registry.addFormatter(new StringFormatter());
			registry.addFormatter(new LocalDateFormatter("dd/MM/yyyy"));
		}
		
		@Override
		public void addViewControllers(ViewControllerRegistry registry) {
			registry.addViewController("/logout-success").setViewName("logout-success");
			registry.addViewController("/login").setViewName("login");
		}
		

		
		
		@Bean(name="messageSource")
		public ResourceBundleMessageSource messageSource() {
			ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
			// i18n/applicationMessages : messages applicatifs (fournis par ex3")
			// uiMessages : messages UI (fournis par ex5")
			messageSource.setBasenames("i18n/applicationMessages", "uiMessages");
			return messageSource;
		}
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { MvcConfiguration.class };
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {ApplicationConfig.class, SecurityConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
	@Override
	protected Filter[] getServletFilters() {
		return new Filter[] {new DelegatingFilterProxy("springSecurityFilterChain")};
	}
}