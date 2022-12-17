# spring-boot-use

本项目整合了`spring-boot`开发中常用的功能和框架。为了方便使用，项目将每一项功能单独建立了一个模块，各个模块都可以单独运行。各个模块都经过真实环境运行，修复了整合时的一些常见坑点，并有详细的注释和参考文档地址。

项目的最终目标：**快速整合开发，避免重复踩坑**。

## 模块功能

| 模块               | 功能点                                 |
|------------------|-------------------------------------|
| use-async        | 异步注解、线程池、异步编排、异步管理器                 |
| use-cache        | SpringCache基本使用、SpringCache整合Redis |
| use-fastdfs      | 对接FastDFS服务：上传、删除、读取、保存文本文件         |
| use-file         | 文件上传、下载、配合nginx文件代理的权限服务            |
| use-ftp          | 对接FTP服务：上传、下载、ftp连接池                |
| use-mybatis-plus | 增删改查、分页、自动填充、代码生成器、全局异常处理           |
| use-rabbitmq     | 对接RabbitMQ服务：可靠投递、延时队列实现 |
| use-redis        | 对接Redis服务：Lettuce框架、Redisson框架、分布式锁 |
| use-schedule     | 基于注解的定时任务、基于接口的定时任务管理器              |
| use-swagger      | 接口注解、类注解、分组配置                       |
| use-valid        | 方法参数校验、实体参数校验、自定义校验、手动校验            |
| use-websocket    | websocket实现、自定义jackson框架工具类         |

## 环境版本

项目使用的是`JDK17`版本，但是项目代码并没有使用其新的语法特性，代码全面兼容`JDK8`版本。

| 框架             | 版本     | 说明           |
|----------------|--------|--------------|
| knife4j        | 3.0.3  | 在线接口文档       |
| druid          | 1.2.6  | 数据库连接池       |
| mybatis-plus   | 3.4.1  | ORM框架        |
| freemarker     | 2.3.30 | 模板引擎         |
| commons-io     | 2.11.0 | IO工具         |
| commons-net    | 3.3    | net工具        |
| commons-pool2  | 2.4.2  | 连接池工具        |
| fastdfs-client | 1.27.1 | FastDFS服务客户端 |
| hutool         | 5.8.10 | Hutool工具     |
| redisson       | 3.18.1 | 分布式Redis框架   |

## 注意事项

* 在`/doc` 文件中包含了数据库所需的sql文件，以及项目所依赖的服务的`docker-compose.yml`文件和`配置`文件。项目各个模块运行前，需要部署该模块所依赖的服务，可直接通过提供的`docker-compose.yml`文件部署`docker`服务。
* 如果使用`JDK8`运行本项目，需要将所有`pom.xml`文件中的`<java.version>`、`<maven.compiler.source>`和`<maven.compiler.target>`编译选项设置为`8`。
