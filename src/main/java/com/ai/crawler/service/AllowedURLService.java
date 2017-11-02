package com.ai.crawler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.crawler.entity.AllowedURL;
import com.ai.crawler.mapper.AllowedURLMapper;

@Service
public class AllowedURLService {
	@Autowired
	AllowedURLMapper allowedURLMapper;

	public boolean delete(AllowedURL allowedURL) {
		return allowedURLMapper.delete(allowedURL);
	}

	public List<AllowedURL> getAllowedList() {
		return allowedURLMapper.getAllowedList();
	}

	public boolean insert(AllowedURL allowedURL) {
		return allowedURLMapper.insert(allowedURL);
	}
}
