package com.project.recyclapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@SpringBootApplication
public class RecyclappApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecyclappApplication.class, args);
	}

	@GetMapping("hello")
	public static String helloWorld() {
		return "<h1>Hello World</h1>";
	}
}
