package com.excilys.java.formation.springmvc.configuration;

import java.util.List;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.excilys.java.formation")
public class RestConfiguration implements WebMvcConfigurer {
	 
	public void configureMessageConverters(
	     List<HttpMessageConverter<?>> converters) {
	       converters.add(new MappingJackson2HttpMessageConverter());
	   }

}
