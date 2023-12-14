CREATE TABLE `blog_title`
(
    `id`          BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `title`       VARCHAR(255) NOT NULL COMMENT '标题',
    `description` VARCHAR(255) NULL DEFAULT NULL COMMENT '描述',
    PRIMARY KEY (`id`) USING BTREE
) COMMENT='博客标题' COLLATE='utf8_general_ci' ENGINE=InnoDB;



INSERT INTO `blog_title` (`id`, `title`, `description`) VALUES
(808, '0.SpringBoot目录', 'https://xiaoxiaofeng.com'),
(809, '1.SpringBoot项目创建', '大家好，我是笑小枫，跟我一起玩转SpringBoot项目吧，本文讲一下如何搭建SpringBoot项目。'),
(810, '10.SpringBoot处理请求跨域问题', 'CORS全称Cross-Origin Resource Sharing，意为跨域资源共享。当一个资源去访问另一个不同域名或者同域名不同端口的资源时，就会发出跨域请求。如果此时另一个资源不允许其进行跨域资源访问，那么访问就会遇到跨域问题。跨域指的是由于浏览器的安全性限制，不允许前端页面访问协议不同、域名不同、端口号不同的http接口。'),
(811, '11.SpringBoot接口日志信息统一记录', '为什么要记录接口日志？\n至于为什么，详细看到这里的小伙伴心里都有一个答案吧，我这里简单列一下常用的场景吧🙈用户登录记录统计、重要增删改操作留痕、需要统计用户的访问次数、接口调用情况统计、线上问题排查、等等等...既然有这么多使用场景，那我们该怎么处理，总不能一条一条的去记录吧🥶面试是不是老是被问Spring的Aop的使用场景，那这个典型的场景就来了，我们可以使用Spring的Aop，完美的实现这个功能，接下来上代码😁'),
(812, '12.SpringBoot导入Excel', '在java处理excel方便从简单的实现功能到自己封装工具类，一路走了好多，阿里的easyExcel对POI的封装更加精简这里介绍一下简单使用。'),
(813, '13.SpringBoot导出Excel', '在java处理excel方便从简单的实现功能到自己封装工具类，一路走了好多，阿里的easyExcel对POI的封装更加精简这里介绍一下简单使用。'),
(814, '14.SpringBoot发送邮件', '本文主要介绍了使用SpringBoot发送邮件，主要包含如何获取发送邮件的授权码，这里以QQ邮箱为例，然后介绍了功能如何实现，包括通过模板发送邮件，发送带图片的邮件，发送带附件的邮件，发送带有多个附件的邮件。'),
(815, '15.SpringBoot根据模板生成Word', '本文主要讲了SpringBoot基于模板的形式生成word的功能实现，感兴趣或有类似功能需求的小伙伴可以看一下，包括word模板制作，功能代码实现，支持导出图片、表格等功能。'),
(816, '16.SpringBoot生成PDF', '本文主要介绍了在SpringBoot项目下，通过代码和操作步骤，详细的介绍了如何操作PDF。希望可以帮助到准备通过JAVA操作PDF的你。\n本文涉及pdf操作，如下：\nPDF模板制作、 基于PDF模板生成，并支持下载、自定义中文字体、完全基于代码生成，并保存到指定目录、合并PDF，并保存到指定目录、合并PDF，并支持下载\n'),
(817, '17.SpringBoot文件上传下载', '在java开发中文件的上传、下载、删除功能肯定是很常见的，本文主要基于上传图片或文件到指定的位置展开，通过详细的代码和工具类，讲述java如何实现文件的上传、下载、删除。'),
(818, '18.SpringBoot中的Properties配置', 'springboot在使用过程中，我们有很多配置，比如mysql配置、redis配置、mybatis-plus、调用第三方的接口配置等等...\n\n我们现在都是放在一个大而全的配置里面的，如果我们想根据功能分为不同的配置文件管理，让配置更加清晰，应该怎么做呢？'),
(819, '19.使用Docker部署最佳实践', '使用Docker部署最佳实践'),
(820, '2.SpringBoot配置基于swagger2的knife4j接口文档', 'SpringBoot项目如果前后端分离，怎么把写好了的接口返回给前端的小伙伴呢，试试这款基于Swagger2的knife4j吧，简直好用到爆！'),
(821, '3.SpringBoot集成Mybatis Plus', '本文主要介绍了SpringBoot集成mysql数据库、集成Mybatis Plus框架；通过一个简单的例子演示了一下使用Mybatis Plus进行数据插入和查询；使用Knife4j进行接口调试；集成阿里巴巴Druid数据连接池；通过Druid页面进行执行sql查询、分析。'),
(822, '4.SpringBoot返回统一结果包装', '前后端分离的时代，如果没有统一的返回格式，给前端的结果各式各样，估计前端的小伙伴就要骂娘了。  \n我们想对自定义异常抛出指定的状态码排查错误，对系统的不可预知的异常抛出友好一点的异常信息。  \n我们想让接口统一返回一些额外的数据，例如接口执行的时间等等。  那就进来一起康康吧~......'),
(823, '5.SpringBoot返回统一异常处理', '如果程序抛异常了，我们是否也可以返回统一的格式呢？\n答案是，当然可以的，不光可以抛出我们想要的格式，还可以对指定的异常类型进行特殊处理\n例如使用@Validated对入参校验的异常，我们自定义的异常等等...'),
(824, '6.SpringBoot日志打印Logback详解', 'Logback 旨在作为流行的 log4j 项目的继承者，是SpringBoot内置的日志处理框架，spring-boot-starter其中包含了spring-boot-starter-logging，该依赖内容就是 Spring Boot 默认的日志框架 logback。这里给大家介绍一下在SpraingBoot中Logback的配置。'),
(825, '7.SpringBoot控制台自定义banner', '熬夜整理完logback相关的内容，突然发现我们的《笑小枫系列-玩转SpringBoot》已经6篇文章了，我们的配套程序居然没有一个属于自己的log，这简直说不过去了，我这处女座的小暴脾气，赶紧整一个，于是便有了此文。好了，接下来言归正传，毕竟本文也是属于我们系列的一份子嘛，不能落下🙈'),
(826, '8.SpringBoot集成Redis', 'SpringBoot中怎么使用Redis做缓存机制呢？本文为大家揭开Redis的面纱，内容偏基础，但详细。本文核心：SpringBoot继承redis、SpringBoot常用的redis操作演示、监听Redis的key过期机制。'),
(827, '9.SpringBoot用户登录拦截器', '本文主要介绍了SpringBoot实现登录功能，使用JWT+Redis进行功能实现，从最基础的建表开始，详细的介绍了功能的实现。学习完本文，你将掌握登录功能的核心技能。'),
(830, '【笑小枫的按步照搬系列】IDEA热部署神器-JRebel安装破解', '使用JRebel让你在开发过程中，无需重启项目即可让代码改动快速生效，极大提高效率，可谓是必备IDEA插件。'),
(831, '【笑小枫的按步照搬系列】Java开发工具IDEA安装及破解', 'IDEA 2021.3.1 版本破解简介支持全家桶，如idea,pycharm,webstorm,goland…等等，亲测有效，请放心使用~针对最新 2021.3.1版本可以做到永久激活、直接激活99年！'),
(832, '【笑小枫的按步照搬系列】JDK8下载安装配置', '本文主要讲解了JDK8在windows环境下的下载、安装、已经环境变量的配置，参照本文，你只需要按步照搬，便可快速的安装好JAVA环境。'),
(833, '【笑小枫的按步照搬系列】Maven环境配置', '本文主要介绍了maven的安装配置，包括配置本地仓库，配置阿里镜像等。安装maven环境之前要先安装java jdk环境（没有安装java环境的可以先去看安装JAVA环境的教程）Maven 3.3+ require JDK 1.7 及以上。'),
(834, '【笑小枫的按步照搬系列】Node.js安装', 'Node.js安装'),
(835, '【笑小枫的按步照搬系列】Redis可视化工具-RedisInsight', 'RedisInsight是Redis官方出品的可视化管理工具，可用于设计、开发、优化你的Redis应用。支持深色和浅色两种主题，界面非常炫酷！可支持String、Hash、Set、List、JSON等多种数据类型的管理，同时支持远程使用CLI功能，功能非常强大！'),
(836, '【笑小枫的按步照搬系列】Redis多系统安装（Windows、Linux、Ubuntu）', 'Redis（Remote Dictionary Server )，即远程字典服务，是一个开源的使用ANSI C语言编写、支持网络、可基于内存亦可持久化的日志型、Key-Value数据库，并提供多种语言的API。本文主要讲述了Redis如何安装。'),
(837, '【笑小枫的按步照搬系列】开源的服务器远程工具-FinalShell', '之前一直使用 xshell + ftp 组合的方式来部署项目，后来发现了FinalShell 这款软件，瞬间就爱上了。FinalShell 相当于 xshell + ftp 的组合，即：FinalShell = xshell + ftp ；FinalShell 只用一个程序，将xshell 、ftp同屏显示，既可以输入命令，也可以传输数据，还能以树的形式展示文件路径。'),
(838, '【笑小枫的按步照搬系列】数据库可视化工具Navicat安装及破解', '怎么连接mysql数据库呢？本文介绍一个数据可视化工具navicat，包括navicat的下载、安装、破解、连接，一站式好文，还不赶紧收藏起来备用~'),
(839, '【笑小枫的按步照搬系列】数据库连接-DataGrip保姆级教程', '怎么连接mysql数据库呢？使用可视化工具连接mysql，市面上常见的一般是使用Navicat和DataGrip，本文主要介绍的是DataGrip的安装以及使用的保姆级介绍。相信如果你想入坑DataGrip，本文一定不容错过。'),
(840, '【笑小枫的按步照搬系列】本地安装Mysql数据库', '本文主要介绍了在windows环境下如何下载安装mysql8+版本，你只需要按步照搬就可以完美解决你安装软件的困扰。本文主要包括mysql的下载、安装、配置my.ini文件、修改初始化密码等。'),
(841, '【笑小枫的按步照搬系列】版本控制工具git安装过程详解', 'Git 是个免费的开源分布式版本控制系统，下载地址为git-scm.com 或者 gitforwindows.org，本文介绍  Git-2.35.1.2-64-bit.exe 版本的安装方法，需要的小伙伴可以看一看。');


