# blog项目
------------------------------------
普通的小型个人博客,由于本人目前前后分离,故而web的开发程度不高.


项目简介
----------------------------

开发人员:安永亮 

初始时间:2018-11-1

开发环境所需：

windows 10 || MAC || windows 7

jdk1.8 

gradle 5.4

开发工具：IEEA 2019, sublime text 3, Hbuilder X

实例地址：www.anyongliang.cn 

服务器环境： centos7以上(为了安装DB方便,请选用7,推荐7.5)

项目集成框架
-----------------------------------
web：

1:JQuery

2:i18n(国际化)

3:bootstrap框架

server:

1:springboot

2:quartz（定时器）

3:shiro（权限）

4:Redison（核心功能并发）

数据库
-----------------------------------
Redis : 用户缓存

mongoDB : Js的存储

MariaDB 10.3: 基于Mysql的关系型数据库,用的是较新的版本,或者至少10.2.1以上

搭建流程
------------------------------------
下载源码,用gradle管理,idea进行开发,设置jdk1.8

修改Const.class里的数据库连接(默认的都是127.0.0.1)

导入MaraiDB表:blog.sql

启动:Application.class

浏览器打开:127.0.0.1:8080