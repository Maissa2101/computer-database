package com.excilys.java.formation.springmvc.configuration;

import java.util.Locale;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.excilys.java.formation.service.ValidatorException;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.excilys.java.formation")
@Profile("!interface")
public class SpringMvcConfiguration extends WebMvcConfigurerAdapter implements WebMvcConfigurer {
	
	@Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/computer-database-db?useSSL=false&serverTimezone=CET");
        dataSource.setUsername("admincdb");
        dataSource.setPassword("qwerty1234");
        return dataSource;
    }
	
	@Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
 
        return viewResolver;
    }
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeInterceptor());
    }
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	      registry.addResourceHandler("/static/**").addResourceLocations("/static/");
	  }
	
	@Bean
    public PlatformTransactionManager txManager() {
        DataSourceTransactionManager txManager = new DataSourceTransactionManager();
        txManager.setDataSource(dataSource());
        return txManager;
    }
	
	 @Bean
	    public ReloadableResourceBundleMessageSource messageSource(){
	        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	        messageSource.setBasename("classpath:messages");
	        messageSource.setDefaultEncoding("UTF-8");
	        return messageSource;
	    }

	    @Bean
	    public CookieLocaleResolver localeResolver(){
	        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
	        localeResolver.setDefaultLocale(Locale.ENGLISH);
	        localeResolver.setCookieName("my-locale-cookie");
	        localeResolver.setCookieMaxAge(3600);
	        return localeResolver;
	    }

	    @Bean
	    public LocaleChangeInterceptor localeInterceptor(){
	        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
	        interceptor.setParamName("lang");
	        return interceptor;
	    }
	    
	    @Bean
	    public static PropertySourcesPlaceholderConfigurer
	    propertySourcesPlaceholderConfigurer() {
	        return new PropertySourcesPlaceholderConfigurer();
	    }

	    @Bean
	    HandlerExceptionResolver customExceptionResolver() {
	        SimpleMappingExceptionResolver s = new SimpleMappingExceptionResolver();
	        Properties p = new Properties();
	        p.setProperty(NoHandlerFoundException.class.getName(), "404");
	        s.setExceptionMappings(p);
	        s.addStatusCode("404", HttpStatus.NOT_FOUND.value());
	        s.addStatusCode("500", HttpStatus.INTERNAL_SERVER_ERROR.value());
	        s.addStatusCode("403", HttpStatus.FORBIDDEN.value());
	        s.setOrder(Ordered.HIGHEST_PRECEDENCE);
	        return s;
	    }
}
