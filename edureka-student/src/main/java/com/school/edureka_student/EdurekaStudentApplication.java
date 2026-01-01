package com.school.edureka_student;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EdurekaStudentApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdurekaStudentApplication.class, args);
	}

}
