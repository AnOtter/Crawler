package com.ai.crawl2;

/**
 * @author OTTER
 * @接口说明 网页获取器观察者接口
 * @方法 pageFeched 当页面 获取之后的动作
 */
public interface FetcherObserver {
	public void pageFetched(WebPage webPage);
}
