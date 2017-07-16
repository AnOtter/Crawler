package com.ai;

import java.net.MalformedURLException;
import java.net.URL;

import com.ai.crawl.WebPage;
import com.ai.crawl.XinHuaPage;;

public class App {	
	
	public static void main(String[] args) {
		WebPage page=new XinHuaPage();
		try {
			page.setUrl(new URL("http://news.xinhuanet.com/politics/2017-07/16/c_1121327252.htm"));
			if(page.fetch())
			{
				page.getTitle();
				page.getArticleContent();
				page.save();
			}
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	
			

}
