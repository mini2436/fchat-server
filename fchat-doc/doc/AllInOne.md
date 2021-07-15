# FChat接口文档
Version |  Update Time  | Status | Author |  Description
---|---|---|---|---
0.0.1-dev||创建状态|mini2436|面试项目API接口文档



## 测试API
### Redis测试接口
**URL:** https://fchat-api-dev.mini2436.xyz/test/

**Type:** GET

**Author:** mini2436

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** Redis测试接口

**Request-headers:**

Header | Type|Description|Required|Since
---|---|---|---|----
fchat-token|string|该值在登陆接口中返回,后续接口调用必传|true|-


**Request-example:**
```
curl -X GET -k -H 'fchat-token' -i https://fchat-api-dev.mini2436.xyz/test/
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
code|int32|返回状态自定义code|-
msg|string|返回消息定义|-
data|object|返回请求体定义|-
└─any object|object|any object.|-

**Response-example:**
```
{
	"code": 580,
	"msg": "syrka7",
	"data": {
		"mapKey": {
			"waring": "You may use java.util.Object for Map value; smart-doc can't be handle."
		}
	}
}
```

### Mysql异步测试接口
**URL:** https://fchat-api-dev.mini2436.xyz/test/

**Type:** POST

**Author:** mini2436

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** Mysql异步测试接口

**Request-headers:**

Header | Type|Description|Required|Since
---|---|---|---|----
fchat-token|string|该值在登陆接口中返回,后续接口调用必传|true|-


**Request-example:**
```
curl -X POST -k -H 'fchat-token' -i https://fchat-api-dev.mini2436.xyz/test/
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
code|int32|返回状态自定义code|-
msg|string|返回消息定义|-
data|object|返回请求体定义|-

**Response-example:**
```
{
	"code": 368,
	"msg": "hfqxyn",
	"data": true
}
```

### 根据id查询数据库中的测试数据
**URL:** https://fchat-api-dev.mini2436.xyz/test/{id}

**Type:** GET

**Author:** mini2436

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 根据id查询数据库中的测试数据

**Request-headers:**

Header | Type|Description|Required|Since
---|---|---|---|----
fchat-token|string|该值在登陆接口中返回,后续接口调用必传|true|-


**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|int32|查询的用户主键|true|-

**Request-example:**
```
curl -X GET -k -H 'fchat-token' -i https://fchat-api-dev.mini2436.xyz/test/990
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
code|int32|返回状态自定义code|-
msg|string|返回消息定义|-
data|object|返回请求体定义|-
└─id|int32|主键|-
└─name|string|名称|-
└─phone|string|手机号|-
└─age|int32|年龄|-

**Response-example:**
```
{
	"code": 114,
	"msg": "phdru1",
	"data": {
		"id": 95,
		"name": "笑愚.曾",
		"phone": "15239181587",
		"age": 38
	}
}
```


