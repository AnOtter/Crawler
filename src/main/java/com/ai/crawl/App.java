package com.ai.crawl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class App {
		public static void main(String[] args) {
		try {
			SpringApplication.run(App.class);
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}

	public static void crawl() {
		FetchManager fetchManager = FetchManager.getInstance();
		fetchManager.run();
	}

}
