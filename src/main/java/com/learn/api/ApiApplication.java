package com.learn.api;

import com.learn.api.controller.PersonController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackageClasses = PersonController.class)
@EnableMongoRepositories(basePackages = "com.learn.api.repositories")
//@ComponentScan({"com.learn.api"})
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
}
