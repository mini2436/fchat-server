# FChat-Server

## 项目简介

- 本项目用于2021年下半年面试项目展示，无商业性质，仅作个人的技能展示。项目整体基于SpringBoot2.5.2版本构建，整体架构在WebFlux的异步交互体系之上，数据库采用R2DBC的异步方案，项目使用Docker 镜像进行容器化部署，方便后续进行Kubernetes的集群迁移。日志收集采用了较为轻量的LOKI日志系统，与Granfana相互集成，进行日志的可视化分析也能方便的对系统运行情况进行监控。

## 项目架构流程

- 项目流程主要分为三个部分 `API服务` `消息服务` 与 `WebSocket` 服务，其中用户层直接与API服务进行交互，通过API服务进行系统中的一系列操作，普通请求直接在API服务进行处理，需要消息推送的特殊请求，采用消息队列进行解耦，将消息推送到消息服务，然后消息服务在统一针对WebSocket服务进行消息推送。
- 整体流程图如下所示:
- <img src="http://edrawcloudpubliccn.oss-cn-shenzhen.aliyuncs.com/viewer/self/23806526/share/2021-7-6/1625542216/main.svg">
- 系统登录流程分析如下图所示:
- <img src="http://edrawcloudpubliccn.oss-cn-shenzhen.aliyuncs.com/viewer/self/23806526/share/2021-7-6/1625542363/main.svg">

## 基础功能分析

- 项目的功能就是参考微信，然后进行精简，毕竟有些功能目前实力不允许也做不出来
- <img src="http://edrawcloudpubliccn.oss-cn-shenzhen.aliyuncs.com/viewer/self/23806526/share/2021-7-6/1625542503/main.svg">

## CI自动集成构建

- 项目CI工具选型为DroneCI，没有采用Jenkins的原因是个人觉得Jenkins太过于繁重了，不利于小型化项目的自动集成。而选用DroneCI的另一个原因是DroneCI与Gitea集成，相当方便,且内存占用相当Jenkins也少了很多
- <img src="http://edrawcloudpubliccn.oss-cn-shenzhen.aliyuncs.com/viewer/self/23806526/share/2021-7-6/1625542729/main.svg">

## 开发工具及相关版本

|     名称     | 版本             |
| :----------: | ---------------- |
|   开发语言   | AdoptOpenJDK 16  |
|   开发工具   | IDEA 2021.1.2 CE |
|     容器     | Docker 19.03.9   |
| 持久化数据库 | MySQL 8.0.24     |
|  缓存数据库  | Redis 6.2.1      |
|  文档数据库  | MongoDB 4        |
|  文件服务器  | SeaweedFS 2.5.7  |
|   消息队列   | RabbitMQ         |
|   开发框架   | SpringBoot 2.5.2 |

## 工程目录结构





