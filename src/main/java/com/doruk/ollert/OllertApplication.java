package com.doruk.ollert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class OllertApplication extends SpringBootServletInitializer {

	@Autowired
	static DataGenerator dataGenerator;
	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(OllertApplication.class);

		builder.headless(false);

		ConfigurableApplicationContext context = builder.run(args);

	}
		@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:3000");
			}
		};
	}

}