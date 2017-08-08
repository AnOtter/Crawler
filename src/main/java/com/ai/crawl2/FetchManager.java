package com.ai.crawl2;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
@Component
public class FetchManager implements ApplicationContextAware {

	@Autowired
	PageFetcher pageFetcher;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
				
	}
	
	@PostConstruct
	public void run(){
		try {
			WebPage page=new WebPage("http://www.qq.com");
			pageFetcher.fetch(page);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

}
