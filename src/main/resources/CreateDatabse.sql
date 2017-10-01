--创建数据库
CREATE DATABASE crawler charset utf8;

use crawlser;
--允许爬取网页列表
CREATE TABLE T_AllowedURList (
  Authority varchar(50) NOT NULL,
  PRIMARY KEY (`Authority`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--文章内容匹配正则式表
CREATE TABLE `T_ArticlePattern` (
  `Authority` varchar(32) NOT NULL,
  `MatchPattern` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--标题匹配正则式表
CREATE TABLE `T_TitlePattern` (
  `Authority` varchar(200) NOT NULL,
  `MatchPattern` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--页面信息表
CREATE TABLE `T_WebPages` (
  `PageIdentity` bigint(20) NOT NULL AUTO_INCREMENT,
  `URL` varchar(400) NOT NULL,
  `ParentURL` varchar(256) DEFAULT NULL,
  `FetchTime` datetime DEFAULT NULL,
  `Title` varchar(128) DEFAULT NULL,
  `FetchingTime` datetime DEFAULT NULL,
  `PublishDate` VARCHAR(8) DEFAULT NULL,
  PRIMARY KEY (`PageIdentity`),
  UNIQUE KEY `IX_WebPages_URL` (`URL`),
  KEY `IX_Pages_FetchTime` (`FetchTime`),
  KEY `IX_Pages_ParentURL` (`ParentURL`),
  KEY `IX_Pages_FetchingTime` (`FetchingTime`),
  KEY `IX_Webpages_Title` (`Title`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

