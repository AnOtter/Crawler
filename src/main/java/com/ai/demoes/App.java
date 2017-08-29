package com.ai.demoes;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages="com.ai.demos")
public class App {
	public static void main(String[] args) {		
		try {
			SpringApplication.run(App.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
