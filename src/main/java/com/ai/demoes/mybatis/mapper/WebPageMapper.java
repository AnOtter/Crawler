package com.ai.demoes.mybatis.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ai.demoes.mybatis.entity.WebPage;

@Component
public interface WebPageMapper {
	List<WebPage> getPageList(int count);
}
