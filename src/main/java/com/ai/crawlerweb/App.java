package com.ai.crawlerweb;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ai.crawler.entity.WebPage;
import com.ai.crawler.service.WebPageService;

@RestController
@SpringBootApplication
@ComponentScan(basePackages = "com.ai")
@MapperScan(basePackages = "com.ai.crawler")
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Autowired
	WebPageService webPageService;

	@RequestMapping("/qq")
	String date() {
		return "<html><title>你好</title><body><a href='http://www.qq.com'>腾讯网</a></body></html>";
	}

	@RequestMapping("/fetchList")
	List<WebPage> getFetchList() {
		return webPageService.getFetchList(5);
	}

	@RequestMapping(value = "/page/{pageIdentity}", method = RequestMethod.GET)
	public ResponseEntity<WebPage> getPageByIdentity(@PathVariable("pageIdentity") long pageIdentity) {
		WebPage webPage = webPageService.getPageByIdentity(pageIdentity);
		HttpStatus responseStatus = webPage == null ? HttpStatus.NOT_FOUND : HttpStatus.OK;
		return new ResponseEntity<WebPage>(webPage, responseStatus);
	}

	@RequestMapping(value = "/word", method = RequestMethod.GET)
	public List<WebPage> getPageByKeyWord(@RequestParam("keyword") String keyword,
			@RequestParam(value = "count", defaultValue = "5") int count) {
		return webPageService.getPagesByKeyWord(keyword, count);
	}

	@RequestMapping("/")
	String home() {
		return "Welcome to Spring RESTful MVC";
	}

}
