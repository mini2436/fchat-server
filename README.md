<h1 align="center">Fchat</h1>
<p align="center">本项目用于2021年下半年面试项目展示，无商业性质。项目采用MIT许可协议可随意分发,依赖版本全部采用最新版本的相关开源组件,个人编译该项目请参考如下标记的版本,自行解决版本依赖等相关问题.</p>
<div>


<p align="center">
    <a href="#">
        <img src="https://img.shields.io/badge/license-MIT-green" alt="MIT License" />
    </a>
    <a href="https://drone.mini2436.xyz/mini2436/FChat-Server">
  		<img src="https://drone.mini2436.xyz/api/badges/mini2436/FChat-Server/status.svg?ref=refs/heads/dev" />
		</a>
    <a href="#">
        <img src="https://img.shields.io/badge/author-mini2436-yellowgreen" alt="mini2436">
    </a>
    <a href="#">
        <img src="https://img.shields.io/badge/AdoptOpenJDK-16.0.0-blue" alt="jdk">
    </a>
    <a href="#">
        <img src="https://img.shields.io/badge/maven-3.8.1-aquamarine" alt="maven">
    </a>
    <a href="#">
        <img src="https://img.shields.io/badge/MySQL-8.0.24-green" alt="mysql">
    </a>
    <a href="#">
        <img src="https://img.shields.io/badge/docker-19.03.9-dodgerblue" alt="docker">
    </a>
    <a href="#">
        <img src="https://img.shields.io/badge/Redis-6.2.1-red" alt="redis">
    </a>
    <a href="#">
        <img src="https://img.shields.io/badge/MongoDB-4.0.0-springgreen" alt="MongoDB">
    </a>
    <a href="#">
        <img src="https://img.shields.io/badge/SeaweedFS-2.5.7-cornflowerblue" alt="SeaweedFS">
    </a>
    <a href="#">
        <img src="https://img.shields.io/badge/RabbitMQ-3.8.19-lightpink" alt="RabbitMQ">
    </a>
    <a href="#">
        <img src="https://img.shields.io/badge/SpringBoot-2.5.2-greenyellow" alt="SpringBoot">
    </a>
</p>

## 项目简介

项目整体基于SpringBoot2.5.2版本构建，整体架构在WebFlux的异步交互体系之上。采用WebFlux的原因是服务器体量小,据说这个对内存要求没有Servlet服务器要求高,且写法上相比传统JAVA写法更接近搬砖,所以就准备尝试一波。整体体验下来除了启动速度快一些，并没有相比Tomcat的传统Servlet服务器表现出什么特别的优势。为了WebFlux能发挥出正常水平项目中的各个中间件的连接客户端也都调整为了Reactive版本，Redis与MongoDB都是如此，当然MySQL客户端也使用的是R2DBC，网络请求采用的Spring提供的WebClient异步客户端。项目中用了JDK14以后的一些语法，建议采用JDK16进行编译（2021-07最新版本为16），17应该也是完美兼容的。

总结：WebFlux目前来讲就是一垃圾，并没有什么卵用，建议对性能吞吐量有要求的上Vert.X

## 项目基础结构与部分流程分析

- 项目流程主要分为三个部分 `API服务` `消息服务` 与 `WebSocket` 服务，其中用户层直接与API服务进行交互，通过API服务进行系统中的一系列操作，普通请求直接在API服务进行处理，需要消息推送的特殊请求，采用消息队列进行解耦，将消息推送到消息服务，然后消息服务在统一针对WebSocket服务进行消息推送。
- 整体流程图如下所示:
- <img src="http://edrawcloudpubliccn.oss-cn-shenzhen.aliyuncs.com/viewer/self/23806526/share/2021-7-6/1625542216/main.svg">
- 系统登录流程分析如下图所示:
- <img src="http://edrawcloudpubliccn.oss-cn-shenzhen.aliyuncs.com/viewer/self/23806526/share/2021-7-6/1625542363/main.svg">

## 基础功能分析

- 项目的功能就是参考微信，然后进行部分功能的删除
- <img src="http://edrawcloudpubliccn.oss-cn-shenzhen.aliyuncs.com/viewer/self/23806526/share/2021-7-6/1625542503/main.svg"/>

## CI自动集成构建

- 项目CI工具选型为DroneCI，界面比较现代化，没有采用Jenkins的原因是个人觉得Jenkins太过于繁重了，不利于小型化项目的自动集成。而选用DroneCI的另一个原因是DroneCI与Gitea集成，相当方便,且内存占用相当Jenkins也少了很多。
- 自动构建流程
<img src="http://edrawcloudpubliccn.oss-cn-shenzhen.aliyuncs.com/viewer/self/23806526/share/2021-7-6/1625542729/main.svg">
- 构建页面截图
![image-20210728094456730](https://resource.mini2436.xyz/uploads/2021/07/image-20210728094456730.png)
![image-20210728094739189](https://resource.mini2436.xyz/uploads/2021/07/image-20210728094739189.png)

## 项目文档

### 接口文档

[项目接口文档自动化构建访问地址(smart-doc + nginx +docker进行搭建)](https://fchat-doc.mini2436.xyz/fchat%E6%8E%A5%E5%8F%A3%E6%96%87%E6%A1%A3.html)

![image-20210728091952926](https://resource.mini2436.xyz/uploads/2021/07/image-20210728091952926.png)

### 数据库文档

[项目数据库文档自动化构建访问地址(screw + nginx + docker进行搭建)](https://fchat-doc.mini2436.xyz/fchat%E6%95%B0%E6%8D%AE%E5%BA%93%E6%96%87%E6%A1%A3.html)

![image-20210728092041636](https://resource.mini2436.xyz/uploads/2021/07/image-20210728092041636.png)

## 工程目录结构





