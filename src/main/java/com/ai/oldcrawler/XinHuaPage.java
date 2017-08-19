package com.ai.oldcrawler;

import org.jsoup.select.Elements;

public class XinHuaPage extends WebPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4276245436673693097L;
	
	public XinHuaPage() {
		articleMatchPattern="id=p-detail";
	}
	@Override
	protected void parserTitle(){
		if(document!=null)
		{
			Elements titles=document.select("h1");
			if(titles.size()==0){
				titles=document.select("div[class=h-title]");
			}
			if(titles.size()>=0)
				title=titles.get(0).text();				
		}
	}

}
