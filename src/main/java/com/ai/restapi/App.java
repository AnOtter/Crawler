package com.ai.restapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai.util.DateTime;

@RestController
@SpringBootApplication
@ComponentScan(basePackages = "com.ai")
@MapperScan(basePackages = "com.ai.crawler")
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
	@RequestMapping("/")
	String welcome(){
		return "Welcome to crawler api";
	}
	
	@RequestMapping("/time")
	String currentTime(){
		return DateTime.now();
	}
	
	@RequestMapping("/version")
	String getVersion(){
		return "1.0.0_20170929";
	}	
}