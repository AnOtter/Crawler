package com.ai.demoes.mybatis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.demoes.mybatis.entity.WebPage;
import com.ai.demoes.mybatis.mapper.WebPageMapper;
@Service
public class WebPageService {
	@Autowired 
	private WebPageMapper webPageMapper;
	public List<WebPage> getPageList(int count){
		return webPageMapper.getPageList(count);
	}
}
