package com.ai.crawler.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
//@Component
public class LogAdviser {	
	@Autowired
	Log log;
	
	@Before("execution(* com.ai.crawl2.PageFetcher.fetch(..))")
	public void logBeforeFetch(JoinPoint pjp){
		log.logInfo("fetching URL");
		Object[] args=pjp.getArgs();
		for(Object arg:args){
			log.logInfo(arg);
		}
	}
	
	@AfterThrowing(pointcut="execution(* com.ai.crawl2.DruidPool.*(..))",
			throwing="ex")
	public void logExecuteSQL(Throwable ex){
		log.logError("executing SQL Error");
		log.logError(ex);
	}	
	
//	@Before("execution(* com.ai.crawl2.DruidPool.*(..))")
//	public void logBeforeExecuteSQL(JoinPoint pjp){
//		log.logInfo("execute SQL");
//		Object[] args=pjp.getArgs();
//		for(Object arg:args){
//			log.logInfo(arg);
//		}
//	}	
	
	@AfterThrowing(pointcut="execution(* com.ai.crawl2.PageFetcher.fetch(..))",
			throwing="ex")
	public void logFetch(Throwable ex){
		log.logError(ex);		
	}
}
