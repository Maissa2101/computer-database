package com.excilys.java.formation.springmvc.configuration;


import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Profile("!interface")
public class DashboardInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	 
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { SpringMvcConfiguration.class };
    }
  
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }
  
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
}