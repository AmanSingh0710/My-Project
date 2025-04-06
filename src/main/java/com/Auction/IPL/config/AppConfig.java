package com.Auction.IPL.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer{

     // Inject allowed origins from application.properties or application.yml
     @Value("${cors.allowed-origins}")
     private String allowedOrigins;
    
       // Bean for DTO Mapping
       @Bean
       public ModelMapper modelMapper() {
           return new ModelMapper();
       }
   
       // CORS Configuration (Allows Frontend to Access APIs)
       @Override
       public void addCorsMappings(CorsRegistry registry) {
           registry.addMapping("/**") // Allow all endpoints
                   .allowedOrigins(allowedOrigins) // Replace with frontend URL
                   .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow specific HTTP methods
                   .allowedHeaders("*")// Allow all headers
                   .allowCredentials(true);// Allow credentials (cookies, authorization headers, etc.)
       }
}

