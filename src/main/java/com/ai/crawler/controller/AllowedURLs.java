package com.ai.crawler.controller;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.crawler.entity.AllowedURL;
import com.ai.crawler.service.AllowedURLService;

@Component
public class AllowedURLs {
	@Autowired
	AllowedURLService allowedURLService;

	private List<AllowedURL> allowedList;

	public List<AllowedURL> getAllowedURLList() {
		return allowedList;
	}

	@PostConstruct
	public void initlizeAllowedList() {
		allowedList = new LinkedList<>();
		allowedList = allowedURLService.getAllowedList();
	}

	public void setAllowedList(List<AllowedURL> allowedList) {
		this.allowedList = allowedList;
	}

}
