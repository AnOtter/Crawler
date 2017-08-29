package com.ai.crawler;

import java.net.URL;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.util.Database;

@Component
public class ArticleMatchPattern {
	@Autowired
	DruidPool druidPool;
	
	private LinkedList<String> authrityList;
	
	private Map<String,String> matchPatterns;
	
	@PostConstruct
	public void initlize() throws Exception{
		matchPatterns=new HashMap<>();
		authrityList=new LinkedList<>();
		String sql="select * from T_ArticlePattern";
		ResultSet patters=null;
		try {
			patters=druidPool.executeQuery(sql);
			matchPatterns =convertDataSetToMap(patters);
		} finally {
			Database.freeDataResult(patters);
		}
	}
	
	private Map<String, String> convertDataSetToMap(ResultSet patternResult) throws Exception{
		Map<String,String> patternMap=new HashMap<String, String>();
		if(patternResult !=null){
			patternResult.first();
			if (patternResult.getRow() > 0) {
				while (!patternResult.isAfterLast()) {
					String authrity=patternResult.getString("Authrity");
					authrityList.add(authrity);
					String matchPattern=patternResult.getString("MatchPattern");
					patternMap.put(authrity, matchPattern);
					patternResult.next();
				}
			}				
		}
		return patternMap;
	}

	public String getMatchPattern(String fetchingURL){
		try {
			URL url=new URL(fetchingURL);
			String urlAuthority=url.getAuthority();
			for(String domain:authrityList){
				if(urlAuthority.contains(domain))
					return matchPatterns.get(domain);
			}
			return "";
		} catch (Exception e) {
			return "";
		}
		
	}
}
