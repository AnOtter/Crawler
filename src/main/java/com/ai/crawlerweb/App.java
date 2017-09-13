package com.ai.crawlerweb;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai.crawler.entity.WebPage;
import com.ai.crawler.service.WebPageService;

@RestController
@SpringBootApplication
@ComponentScan(basePackages="com.ai")
@MapperScan(basePackages = "com.ai.crawler")
public class App {
	@Autowired
	WebPageService webPageService;

	@RequestMapping("/")
	String index(){
		return "Hello Spring Web";
	}
	
	@RequestMapping("/date")
	String date(){
		return "<html><title>你好</title><body><a href='http://www.qq.com'>腾讯网</a></body></html>";
	}
	
	@RequestMapping("/fetchList")
	List<WebPage> getFetchList(){
		return webPageService.getFetchList(5);
	}
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
