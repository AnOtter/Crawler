package com.ai;

import java.net.URL;
import java.util.List;

import com.ai.crawl.TencentPage;
import com.ai.crawl.WebPage;;

public class App {	
	
	public static void main(String[] args) {
		WebPage page=new TencentPage();
		try {
			page.setUrl(new URL("http://www.qq.com"));
			if(page.fetch())
			{
				List<String> urList=page.getOuterLinks();
				for(String url :urList){
					System.out.println(url);
				}
			}
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	
			

}
