package com.ai.crawl;

import java.net.URL;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.lang.Runnable;
import org.dom4j.Element;

import com.ai.util.Log;
import com.ai.xml.XMLDocument;
import static com.ai.crawl.GlobalVariants.*;
import static com.ai.util.Log.*;

public class Fetcher implements Runnable {
	private static final long halfDay = 12 * 3600 * 1000;

	private LinkedList<String> fetchList;
	private TreeMap<String,Date> fetchedList;
	private List<String> allowList;
	private XMLDocument document;
	private String fetchURL;
	
	public void setDocment(XMLDocument document) {
		this.document=document;
	}
	
	public Fetcher(LinkedList<String> fetchList,TreeMap<String,Date> fetchedList,List<String> allowList) {
		this.allowList=allowList;
		this.fetchList=fetchList;
		this.fetchedList=fetchedList;		
	}
	
	@Override
	public void run() {
		if(!fetchURL.equals(""))
		{					
			fetchPage(fetchURL);
		}
	}
	
	/**
	 * 
	 * @描述 获取页面内容到本地 并将页面的子链接及图像链接加入到获取列表
	 * @param page
	 * @author OTTER
	 * @since 20170430
	 * @description
	 * 
	 */
	private void fetchPage(String url) {
		try {
			logDebug("fetching "+url);			
			if (isURLNeedFetch(url)) {	
				synchronized(fetchedList){
					fetchedList.put(url,new Date());
				}
				WebPage page=null;
				URL url2=new URL(url);
				String authority=url2.getAuthority();
				if(authority.contains("xinhuanet.com"))
					page=new XinHuaPage();
				else if(authority.contains("qq.com"))
					page=new TencentPage();
				page.setUrl(url2);
				page.setLocalFilePath(getLocalSaveFile(page.getUrl(), localSaveDirectory));
				if(page.fetch()) {						
					if(!page.getArticleContent().equals(""))
					{
						page.save();
						synchronized (document) {
							Element newNode= document.createNode("FetchedPage");
							newNode.addAttribute("URL", page.getUrl().toString());
							newNode.addAttribute("LocalFile", page.getLocalFilePath());
							newNode.setText(page.getTitle());
							document.appendNode(newNode);
						}											
					}					
					addSubPageToFetchList(page);
				}
			}
		} catch (Exception e) {
			logError("Fetcher fetchPage error:"+url);
		}
	}
	
	/**
	 * @param pageParser
	 * @throws Exception
	 * @author OTTER
	 * @since 20170430
	 * @描述 将页面中的子链接及图像链接加入到获取列表
	 */
	private void addSubPageToFetchList(WebPage page) throws Exception {		
		List<String> subURLs = page.getOuterLinks();
		for (String subURL : subURLs) {
			if(isURLInAllowList(subURL) && isURLNeedFetch(subURL))
			{
				synchronized(fetchList){					
					if (!fetchList.contains(subURL))
					{
						logDebug("add "+subURL);
						fetchList.addLast(subURL);
					}
				}
			}
		}
		logDebug("fetchList Count:"+fetchList.size());
	}
	
	
	public boolean isURLInAllowList(String url) {
		try {
			URL newURL=new URL(url);
			String authority=newURL.getAuthority();
			if(!authority.equals("")){
				for (String allowAuthority : allowList) {
					if(allowAuthority.equals(authority))
						return true;
				}
			}
			return false;	
		} catch (Exception e) {
			return false;
		}			
	}
	
	/**
	 * @描述 获取网页地址对应的本地路径
	 * @param url
	 * @param localRootDirectory
	 * @return localFile
	 * @author OTTER
	 * @since 20170430
	 */
	private String getLocalSaveFile(URL url, String localRootDirectory) {
		try {
			String authority = url.getAuthority();
			String[] dir = authority.split("\\.");
			String domain = "";
			if (dir.length > 1) {
				for (int i = dir.length - 1; i >= 0; i--) {
					domain += dir[i] + "/";
				}
			}
			if (domain.equals("")) {
				domain = authority;
			}
			String path = url.getPath();
			if (path.equals("") || path.endsWith("/"))
				path += "index.html";
			return Paths.get(localRootDirectory, domain, path).toFile().toString();
		} catch (Exception e) {
			logError("getLocalSavePath Error:"+url.toString());
			return "";
		}
	}
	
	
	/**
	 * 
	 * @描述 按上次获取时间是否超过半天判断页面是否需要获取
	 * @param page
	 * @return boolean
	 * @author OTTER
	 * @since 20170430
	 * @ 非htm页面且上次获取时间超过半小时
	 */
	private boolean isURLNeedFetch(String url) {
		boolean isLastFetchTimeBeforeHalfHour=false;
		Date fetchTime =null;

		synchronized(fetchedList){
			fetchTime = fetchedList.get(url);
		}
			
		if (fetchTime == null){			
			isLastFetchTimeBeforeHalfHour= true;
		}
		else {
			if(!url.contains(".htm")){
				long currentTime = new Date().getTime();
				long pageFetchTime = fetchTime.getTime();			
				isLastFetchTimeBeforeHalfHour=((currentTime - pageFetchTime) > halfDay);	
			}	
			else
				isLastFetchTimeBeforeHalfHour=false;				
		}
		return isLastFetchTimeBeforeHalfHour;
	}

	public String getFetchURL() {
		return fetchURL;
	}

	public void setFetchURL(String fetchURL) {
		this.fetchURL = fetchURL;
	}

}
