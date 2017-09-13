package com.ai.crawlerweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ai.crawler.entity.WebPage;
import com.ai.crawler.service.WebPageService;
@RestController
@RequestMapping("/webpage")
public class WebPages {
	@Autowired
	WebPageService webPageService;
	
	@RequestMapping(value="/{url}",method = RequestMethod.GET)
	public WebPage getPageByURL(@PathVariable("url") String url){
		return webPageService.getPageByURL(url);
	}

	@RequestMapping(value="/p/{pageIdentity}",method = RequestMethod.GET)
	public WebPage getPageByURL(@PathVariable("pageIdentity") long pageIdentity){
		return webPageService.getPageByIdentity(pageIdentity);
	}

}
