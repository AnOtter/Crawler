package com.ai.crawler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.crawler.entity.TitlePattern;
import com.ai.crawler.mapper.TitlePatternMapper;

@Service
public class TitlePatternService {
	@Autowired
	TitlePatternMapper titlePatternMapper;

	public List<TitlePattern> getPatterns() {
		return titlePatternMapper.getPatterns();
	}
	
	public boolean delete(TitlePattern titlePattern) {
		return titlePatternMapper.delete(titlePattern);
	}

	public boolean insert(TitlePattern titlePattern) {
		return titlePatternMapper.insert(titlePattern);
	}

	public boolean update(TitlePattern titlePattern) {
		return titlePatternMapper.update(titlePattern);
	}
}
