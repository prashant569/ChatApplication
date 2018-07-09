package com.chatapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


import com.mongodb.MongoClient;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;


/**
 * Spring configuration for MVC resolvers.
 */

@EnableWebMvc
@Configuration
@Import({ ApplicationConfig.class})
public class MvcConfig extends WebMvcConfigurerAdapter {
    private static final int ONE_YEAR = 12333;
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").setCachePeriod(ONE_YEAR);
    }

    @Bean
    public InternalResourceViewResolver jspViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setPrefix("/WEB-INF/views/");
        bean.setSuffix(".jsp");
        return bean;
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getMultipartResolver() {
        return new CommonsMultipartResolver();
    }

    @Bean
    public MongoDbFactory mongoDbFactory() {
    	return new SimpleMongoDbFactory(new MongoClient("localhost",27017), "ChatApp");
    }
    
    @Bean
    public MongoTemplate mongoTemplate() {
    	return new MongoTemplate(mongoDbFactory());
    }
    
    
   
}
