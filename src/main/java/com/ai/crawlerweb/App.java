package com.ai.crawlerweb;

import java.nio.charset.Charset;
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

import com.ai.crawler.ArticleReader;
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
	
	@Autowired
	ArticleReader articleReader;

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
		return webPage==null?
				new ResponseEntity<WebPage>(webPage, HttpStatus.NOT_FOUND):
					new ResponseEntity<WebPage>(webPage, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/page/{pageIdentity}/read", method = RequestMethod.GET)
	public String readPage(@PathVariable("pageIdentity") long pageIdentity,
			@RequestParam(value="charset",defaultValue="") String charsetName) {
		WebPage webPage = webPageService.getPageByIdentity(pageIdentity);
		if(webPage!=null){
			Charset readCharset=Charset.defaultCharset();
			if(!charsetName.equals(""))
				readCharset=Charset.forName(charsetName);
			return articleReader.read(webPage,readCharset);
		}
		else 
			return "Page NOT found";
	}

	@RequestMapping(value = "/word/{keyword}", method = RequestMethod.GET)
	public List<WebPage> getPageByKeyWord(@PathVariable("keyword") String keyword,
			@RequestParam(value = "count",defaultValue="10") int count) {
		return webPageService.getPagesByKeyWord(keyword, count);
	}

	@RequestMapping("/")
	String home() {
		return "<html><head><title>Crawler Board</title></head><body>"
				+"<h1>Welcome to Spring RESTful MVC</h1>"
				+"<h2>/stat</h2>Get the latest pageIdentity"
				+"<h2>/word/keyword?count=count</h2>Query by keyword</p>"
				+"<h2>/page/pageIdentity</h2>Query specific page"
				+"<h2>/page/pageIdentity/read?charset=charsetName</h2>Read specific page"
				+"<h2>/fetchList</h2>Query next 5 webpage to fetch"
				+"<h2>/qq</h2>Visit www.qq.com</body></html>";
	}
	
	@RequestMapping("/stat")
	String stat(){
		long maxPageIdentity=webPageService.getMaxPageIdentity();		
		return "MaxPageIdentity:"+maxPageIdentity;
	}

}
