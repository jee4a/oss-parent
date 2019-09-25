## 项目实施计划
1. 项目框架搭建
2. 业务功能梳理
3. 开发计划排期
4. 项目协作开发
5. 系统功能测试
6. 运营帐号数据，权限收集，预生产系统搭建
7. 试运行，新老系统并行，停更老系统
8. 新老系统切割，正式上线

## 系统总框架图
![系统总框架图](https://github.com/jee4a/oss-parent/raw/master/doc/oss.jpg)

## 技术选型
结合时下主流微服务技术，全面转型spring cloud技术体系
- 微服务框架 ：spring cloud , spring boot 2.1.x
- JVM环境 ：JDK 1.8
- ORM框架：mybatis 3.4.6
- 数据库：mysql 5.7、主从
- 缓存：jvm缓存、分布式缓存redis
- 消息：kafka、rocketmq 4.3.0
- 日志：elk
- 前端：vue
- 搜索：elasticsearch-6.1.2
- 配置管理：Apollo

## 工程结构说明
oss-parent/pom.xml 版本管理，依赖库，环境打包
- oss-framework 公共框架库，登录、权限拦截；优先使用框架内工具类，如没有可拓展继承或自行在本本工程建立
- oss-gateway 网关 ：路由、认证
- oss-eureka 服务注册 	
- 应用层服务
- oss-admin 业务层服务 
- oss-job 任务调度 	
- 微服务	
- oss-member 会员服务 	
- oss-order 订单服务
- oss-product 商品服务 	
- oss-shop 商家，门店服务 	
- oss-pay 支付，充值，提现等	
- oss-settlement 结算	
- oss-activity 活动服务 	
- oss-cms 内容管理 	
- oss-comment 评论服务 	
- oss-promotion 推广服务 	
- oss-stat 统计，报表，多库查询 		
- 基础服务
- oss-auth 权限服务 
- oss-push 消息推送 	
- oss-search 搜索服务 	


## 微服务工程结构说明
以直接返回数据模型对象为主，部分需要聚合业务返回Result&lt;xxxVO&gt;统一输出格式
oss-xxxx-api
- interfaces rpc接口定义
- model 数据模型：与数据库一一对应
- vo 服务层数据聚合模型
	
oss-xxxx-service
- common 公共配置，缓存配置、统一定义，数据源，枚举
- controller 控制层，只做接口数据输入，输出，原则上不写业务
- service 基础业务逻辑，事务 ，缓存，异常向上抛
- mapper 持久层
	
	
## 开发军规
1. 统一返回格式Result
 * success 当前请求是否成功，默认悲观 false：即默认值为失败
 * code 错误码 约定为负数类型 ，例如：-9999
 * message 消息文本，通常用来对 success=false 做进一步描述
 * result 当前业务所需的响应数据
2. 业务方法内部以result.setSuccess()结尾代表业务处理成功，大前端以判断success是否为true做进一步处理
3. 方法及RPC之前调用 可判断canNext()或hasError()是否做进一步处理
4. Controller层原则上不写业务代码，只做输入、输出处理
5. 微服务层：
 * 以直接返回数据模型对象为主，提供全字段处理，需要聚合业务返回Result&lt;xxxVO&gt;统一输出格式，不做异常处理，异常全部往上层抛出
6. 应用业务层
 * 统一输出格式，聚合类定义XxxDTO，返回Result&lt;xxxDTO&gt;
 * service层处理参数校验，建议优先使用框架中Assert，逐个对参数校验；
 * 统一处理异常，并记录日志；


## 开发计划排期
- 项目框架搭建
- 网关服务
- 权限系统
- 客户管理
- 资金管理
- 产品管理
- 借款管理
-

## 人员协作分工

## Q&A

## 电商平台服务化迁移   
此架构模型适合大部分业务平台



