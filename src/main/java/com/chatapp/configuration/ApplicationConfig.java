package com.chatapp.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.chatapp.controller.ChatController;
import com.chatapp.controller.HelloWorldController;
import com.chatapp.controller.LoginAndRegisterController;
import com.chatapp.controller.UserController;
import com.chatapp.dao.UserDao;
import com.chatapp.dao.UserDaoImpl;
import com.chatapp.service.UserService;
import com.chatapp.service.UserServiceImpl;




/**
 * Spring configuration for sample application.
 */
@Configuration
@ComponentScan({ "com.chatapp.configuration" })
@PropertySource("classpath:application.properties")
public class ApplicationConfig {

    /**
     * Retrieved from properties file.
     */
    @Value("${HelloWorld.SiteName}")
    private String siteName;

    @Bean
    public HelloWorldController helloWorld() {
        return new HelloWorldController(this.siteName);
    }
    
   
    @Bean
    public UserController userController() {
    	return new UserController();
    }
    
    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }
    
    @Bean
    public UserDao userDao() {
        return new UserDaoImpl();
    }


    @Bean
    public ChatController chatController() {
    	return new ChatController();
    }
    @Bean
    public LoginAndRegisterController loginAndRegisterController() {
    	return new LoginAndRegisterController();
    }
    

    /**
     * Required to inject properties using the 'Value' annotation.
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
