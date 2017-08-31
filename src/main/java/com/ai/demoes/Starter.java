package com.ai.demoes;

import java.util.List;

import javax.annotation.PostConstruct;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.ai.demoes.mybatis.entity.WebPage;
import com.ai.demoes.mybatis.service.WebPageService;

@Component
@MapperScan(basePackages = "com.ai.demoes.mybatis.mapper")
public class Starter implements ApplicationContextAware {
	@Autowired 
	WebPageService webPageService;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO 自动生成的方法存根
		
	}
	
	@PostConstruct
	public void start(){
		List<WebPage> webPages=webPageService.getPageList(20);
		for(WebPage webPage:webPages){
			System.out.println(webPage);
		}
	}	
}
