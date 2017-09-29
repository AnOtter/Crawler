package com.ai.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ai.crawler.entity.ArticlePattern;
import com.ai.crawler.service.ArticlePatternService;

@RestController
public class ArticlePatternController {
	@Autowired
	ArticlePatternService articlePatternService;

	@RequestMapping(value = "/articlepattern", method = RequestMethod.PUT)
	ResponseEntity<ArticlePattern> create(@RequestBody ArticlePattern articlePattern) {
		if (articlePatternService.insert(articlePattern))
			return new ResponseEntity<>(articlePattern, HttpStatus.CREATED);
		else
			return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
	}

	@RequestMapping(value = "/articlepattern", method = RequestMethod.DELETE)
	ResponseEntity<ArticlePattern> delete(@RequestBody ArticlePattern articlePattern) {
		if (articlePatternService.delete(articlePattern))
			return new ResponseEntity<>(articlePattern, HttpStatus.OK);
		else
			return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
	}

	@RequestMapping(value = "/articles")
	List<ArticlePattern> getArticlePatterns() {
		return articlePatternService.getPatterns();
	}

	@RequestMapping(value = "/articlepattern", method = RequestMethod.POST)
	ResponseEntity<ArticlePattern> update(@RequestBody ArticlePattern articlePattern) {
		if (articlePatternService.update(articlePattern))
			return new ResponseEntity<>(articlePattern, HttpStatus.OK);
		else
			return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
	}
}
