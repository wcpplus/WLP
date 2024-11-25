# WLP
### 免费版官网 [http://wlp.wcpknow.com](http://wlp.wcpknow.com)

#### 介绍
- 本系统为在线学习系统，主要用来教学视频和课件发布，供用户在线学习
-  **支持课件展示** ：PDF课件、MP4课件（H264编码）

#### 当前开源版本v1.0.0
- 增加课程评价功能
- 增加课时评论功能
- 增加专业收藏功能

#### 版本v0.3.1
- 增加专业管理和学习功能
- 增加用户学习权限控制功能
- 增加用户学习记录功能


#### 软件架构
- jdk8
- maven
- spring4
- spring-mvc4
- hibernate4
- bootstrap
- tomcat8
- mysql5.7

#### 代码安装说明
1. maven部署源码（**主模块：WLP/src/wlp-web** ）编译顺序：wlp-api >wlp-core > wlp-parameter > wlp-report > wlp-authority > wlp-quartz > wlp-category  > wlp-fqa  > wlp-simpelfile  > wlp-learn >wlp-tag >wlp-web 
2. 创建数据库，数据库脚本在  WLP/resource/sql/目录下
3. 修改数据库配置文件 WLP/src/wlp-web/src/main/resources/jdbc.properties
4. 修改附件存储地址 WLP/src/wlp-web/src/main/resources/WcpWebConfig.xml (修改参数“config.doc.dir”配置附件的存储路径)
5. 项目编译后可直接部署于tomcat7，mysql5.x中运行，支持jdk7/jdk8，如要使用tomcat8及以上版本可能会有报错，请自行修正（所以建议第一次运行在tomcat7中）

#### 注意事项
1. 建议tomcat7，tomcat8或以上版本可能会有报错，根据错误信息自行百度和修改，并不复杂
2. 目前因为数据库方言的使用，只支持mysql，如果要切换数据库系统会有一些工作量，mysql要配置为大小写不敏感（linux环境下特别注意myslq默认大小写敏感）
3. 请使用utf8字符集

#### wlp在线学习系统，安装包下载

暂未发布

#### 使用说明
暂未发布

#### 界面截图
![输入图片说明](https://images.gitee.com/uploads/images/2020/0716/133334_e2db02d6_24089.png "1.png")

![输入图片说明](https://images.gitee.com/uploads/images/2020/0716/133343_2d4e9883_24089.png "2.png")

![输入图片说明](https://images.gitee.com/uploads/images/2020/0716/133350_75551a6b_24089.png "3.png")

![输入图片说明](https://images.gitee.com/uploads/images/2020/0716/133358_66a242a8_24089.png "4.png")

![输入图片说明](https://images.gitee.com/uploads/images/2020/0716/133411_82fba6e1_24089.png "6.png")

![输入图片说明](https://images.gitee.com/uploads/images/2020/0716/133424_7fada65d_24089.png "7.png")

![输入图片说明](https://images.gitee.com/uploads/images/2020/0716/133437_03f1319c_24089.png "8.png")

![输入图片说明](https://images.gitee.com/uploads/images/2020/0716/133445_58a9442c_24089.png "9.png")


### -----------------------------------------------------------
### [访问WLP官方知识库](http://www.wcpdoc.com/webspecial/home/Pub2c909b2b70cf081d017356243f33434f.html)
### -----------------------------------------------------------



## 开源项目推荐
	
> WCP:知识管理系统 [https://github.com/wcpplus/WCP](https://github.com/wcpplus/WCP)

> WTS:在线答题系统 [https://github.com/wcpplus/WTS](https://github.com/wcpplus/WTS)

> WLP:在线学习系统 [https://github.com/wcpplus/WLP](https://github.com/wcpplus/WLP)

## 商业版产品介绍

> 知识库/在线答題/在线学习产品介绍 [http://www.wcpknow.com/home/index.html](http://www.wcpknow.com/home/index.html)
