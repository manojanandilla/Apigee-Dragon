package com.trigital.cm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import com.mangofactory.swagger.plugin.EnableSwagger;

@EnableAutoConfiguration  // Sprint Boot Auto Configuration
@ComponentScan(basePackages = "com.trigital.cm")
@EnableSwagger // auto generation of API docs
public class TrigitalApiApplication extends SpringBootServletInitializer {
	
	private static final Class<TrigitalApiApplication> applicationClass = TrigitalApiApplication.class;
    private static final Logger log = LoggerFactory.getLogger(applicationClass);
    
    public static void main(String[] args) {
        SpringApplication.run(TrigitalApiApplication.class, args);
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }
}
