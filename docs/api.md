# fchat
Version |  Update Time  | Status | Author |  Description
---|---|---|---|---
dev-v0.0.1|2021年7月27日|创建|mini2436|接口文档完善



## 好友相关API
### 获取当前用户的所有好友列表数据
**URL:** https://fchat-api-dev.mini2436.xyz/friend/

**Type:** GET

**Author:** mini2436

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 获取当前用户的所有好友列表数据

**Request-headers:**

Header | Type|Description|Required|Since
---|---|---|---|----
fchat-token|string|该值在登陆接口中返回,后续接口调用必传|true|-


**Request-example:**
```
curl -X GET -k -H 'fchat-token' -i https://fchat-api-dev.mini2436.xyz/friend/
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
code|int32|返回状态自定义code|-
msg|string|返回消息定义|-
data|array|返回请求体定义|-
└─userId|int64|好友的用户ID|-
└─nickName|string|用户昵称|-
└─avatar|string|头像|-

**Response-example:**
```
{
  "code": 739,
  "msg": "hqhslk",
  "data": [
    {
      "userId": 646,
      "nickName": "novella.purdy",
      "avatar": "3b79f2"
    }
  ]
}
```

### 获取指定好友Id的详细信息数据
**URL:** https://fchat-api-dev.mini2436.xyz/friend/{friendUserId}

**Type:** GET

**Author:** mini2436

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 获取指定好友Id的详细信息数据

**Request-headers:**

Header | Type|Description|Required|Since
---|---|---|---|----
fchat-token|string|该值在登陆接口中返回,后续接口调用必传|true|-


**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
friendUserId|int64|需要查询的好友ID|true|-

**Request-example:**
```
curl -X GET -k -H 'fchat-token' -i https://fchat-api-dev.mini2436.xyz/friend/770
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
code|int32|返回状态自定义code|-
msg|string|返回消息定义|-
data|object|返回请求体定义|-
└─userId|int64|主键|-
└─username|string|用户名|-
└─nickName|string|用户昵称|-
└─avatar|string|头像|-
└─birthday|string|出生日期|-
└─createFriendTime|string|好友添加时间|-

**Response-example:**
```
{
  "code": 379,
  "msg": "wdrcnn",
  "data": {
    "userId": 565,
    "username": "天翊.胡",
    "nickName": "novella.purdy",
    "avatar": "yt0hzk",
    "birthday": "2021-09-29 16:53:48",
    "createFriendTime": "2021-09-29"
  }
}
```

### 删除一个自己的好友
**URL:** https://fchat-api-dev.mini2436.xyz/friend/{friendUserId}

**Type:** DELETE

**Author:** mini2436

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 删除一个自己的好友

**Request-headers:**

Header | Type|Description|Required|Since
---|---|---|---|----
fchat-token|string|该值在登陆接口中返回,后续接口调用必传|true|-


**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
friendUserId|int64|需要删除的用户ID|true|-

**Request-example:**
```
curl -X DELETE -k -H 'fchat-token' -i https://fchat-api-dev.mini2436.xyz/friend/11
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
  "code": 199,
  "msg": "24yuti",
  "data": true
}
```

### 获取自己的好友申请列表
**URL:** https://fchat-api-dev.mini2436.xyz/friend/friendApplyList/{pageIndex}/{pageSize}

**Type:** GET

**Author:** mini2436

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 获取自己的好友申请列表

**Request-headers:**

Header | Type|Description|Required|Since
---|---|---|---|----
fchat-token|string|该值在登陆接口中返回,后续接口调用必传|true|-


**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
pageIndex|int32|页码|true|-
pageSize|int32| 页面条数|true|-

**Request-example:**
```
curl -X GET -k -H 'fchat-token' -i https://fchat-api-dev.mini2436.xyz/friend/friendApplyList/846/10
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
code|int32|返回状态自定义code|-
msg|string|返回消息定义|-
data|array|返回请求体定义|-
└─id|int64|申请信息数据表主键|-
└─applyUserId|int64|申请的用户ID|-
└─applyNickName|string|申请的用户昵称|-
└─applyContent|string|添加的好友信息|-
└─applyTime|string|申请时间|-

**Response-example:**
```
{
  "code": 42,
  "msg": "3cgzcn",
  "data": [
    {
      "id": 710,
      "applyUserId": 510,
      "applyNickName": "novella.purdy",
      "applyContent": "5gz4ki",
      "applyTime": "2021-09-29 16:53:48"
    }
  ]
}
```

### 通过指定的好友申请
**URL:** https://fchat-api-dev.mini2436.xyz/friend/pass/{id}

**Type:** PUT

**Author:** mini2436

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 通过指定的好友申请

**Request-headers:**

Header | Type|Description|Required|Since
---|---|---|---|----
fchat-token|string|该值在登陆接口中返回,后续接口调用必传|true|-


**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|int64|好友申请数据的ID|true|-

**Request-example:**
```
curl -X PUT -k -H 'fchat-token' -i https://fchat-api-dev.mini2436.xyz/friend/pass/120
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
  "code": 42,
  "msg": "bdwxvq",
  "data": true
}
```

### 向指定用户发送好友申请
**URL:** https://fchat-api-dev.mini2436.xyz/friend/apply/{friendId}

**Type:** POST

**Author:** mini2436

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 向指定用户发送好友申请

**Request-headers:**

Header | Type|Description|Required|Since
---|---|---|---|----
fchat-token|string|该值在登陆接口中返回,后续接口调用必传|true|-


**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
friendId|int64|好友ID|true|-

**Query-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
applyUserId|int64|申请的用户ID|false|-
friendUserId|int64|被添加为好友的用户ID|false|-
applyContent|string|申请添加信息|false|-

**Request-example:**
```
curl -X POST -k -H 'fchat-token' -i https://fchat-api-dev.mini2436.xyz/friend/apply/290 --data 'friendUserId=998&applyUserId=453&applyContent=vbz7hk'
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
  "code": 963,
  "msg": "slfirr",
  "data": true
}
```

## 系统API
### 上传文件
**URL:** https://fchat-api-dev.mini2436.xyz/system/uploadFile

**Type:** POST

**Author:** mini2436

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 上传文件

**Request-headers:**

Header | Type|Description|Required|Since
---|---|---|---|----
fchat-token|string|该值在登陆接口中返回,后续接口调用必传|true|-


**Request-example:**
```
curl -X POST -k -H 'fchat-token' -i https://fchat-api-dev.mini2436.xyz/system/uploadFile
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
code|int32|返回状态自定义code|-
msg|string|返回消息定义|-
data|object|返回请求体定义|-
└─url|string|No comments found.|-
└─fid|string|No comments found.|-

**Response-example:**
```
{
  "code": 935,
  "msg": "ccp3xa",
  "data": {
    "url": "www.xn---xn--gtq-c24os58l.net",
    "fid": "88"
  }
}
```

### 根据文件的Fid获取文件的访问连接
**URL:** https://fchat-api-dev.mini2436.xyz/system/fileByFid/{fid}

**Type:** GET

**Author:** mini2436

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 根据文件的Fid获取文件的访问连接

**Request-headers:**

Header | Type|Description|Required|Since
---|---|---|---|----
fchat-token|string|该值在登陆接口中返回,后续接口调用必传|true|-


**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
fid|string|需要获取的文件唯一标识|true|-

**Request-example:**
```
curl -X GET -k -H 'fchat-token' -i https://fchat-api-dev.mini2436.xyz/system/fileByFid/88
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
code|int32|返回状态自定义code|-
msg|string|返回消息定义|-
data|object|返回请求体定义|-
└─url|string|No comments found.|-
└─fid|string|No comments found.|-

**Response-example:**
```
{
  "code": 794,
  "msg": "at3ryo",
  "data": {
    "url": "www.xn---xn--gtq-c24os58l.net",
    "fid": "88"
  }
}
```

### 根据文件的Fid删除文件在文件服务器上面的存储
**URL:** https://fchat-api-dev.mini2436.xyz/system/fileByFid/{fid}

**Type:** DELETE

**Author:** mini2436

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 根据文件的Fid删除文件在文件服务器上面的存储

**Request-headers:**

Header | Type|Description|Required|Since
---|---|---|---|----
fchat-token|string|该值在登陆接口中返回,后续接口调用必传|true|-


**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
fid|string|需要删除的文件唯一标识|true|-

**Request-example:**
```
curl -X DELETE -k -H 'fchat-token' -i https://fchat-api-dev.mini2436.xyz/system/fileByFid/88
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
  "code": 908,
  "msg": "omwzo4",
  "data": true
}
```

## 测试API
### Redis测试接口
**URL:** https://fchat-api-dev.mini2436.xyz/test/getCache

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
curl -X GET -k -H 'fchat-token' -i https://fchat-api-dev.mini2436.xyz/test/getCache
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
  "code": 531,
  "msg": "4dpbnm",
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
  "code": 58,
  "msg": "04661l",
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
curl -X GET -k -H 'fchat-token' -i https://fchat-api-dev.mini2436.xyz/test/139
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
  "code": 570,
  "msg": "u864pg",
  "data": {
    "id": 661,
    "name": "天翊.胡",
    "phone": "15711952059",
    "age": 64
  }
}
```

### 获取系统中所有的测试数据
**URL:** https://fchat-api-dev.mini2436.xyz/test/

**Type:** GET

**Author:** mini2436

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 获取系统中所有的测试数据

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
data|array|返回请求体定义|-
└─id|int32|主键|-
└─name|string|名称|-
└─phone|string|手机号|-
└─age|int32|年龄|-

**Response-example:**
```
{
  "code": 534,
  "msg": "mbo9xg",
  "data": [
    {
      "id": 328,
      "name": "天翊.胡",
      "phone": "15711952059",
      "age": 64
    }
  ]
}
```

### 添加mongoDB文档数据
**URL:** https://fchat-api-dev.mini2436.xyz/test/addDocument

**Type:** POST

**Author:** mini2436

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 添加mongoDB文档数据

**Request-headers:**

Header | Type|Description|Required|Since
---|---|---|---|----
fchat-token|string|该值在登陆接口中返回,后续接口调用必传|true|-


**Request-example:**
```
curl -X POST -k -H 'fchat-token' -i https://fchat-api-dev.mini2436.xyz/test/addDocument
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
code|int32|返回状态自定义code|-
msg|string|返回消息定义|-
data|object|返回请求体定义|-
└─id|int32|No comments found.|-
└─name|string|No comments found.|-
└─phone|string|No comments found.|-
└─age|int32|No comments found.|-

**Response-example:**
```
{
  "code": 766,
  "msg": "5jmley",
  "data": {
    "id": 745,
    "name": "天翊.胡",
    "phone": "15711952059",
    "age": 64
  }
}
```

## 用户API提供用户数据的相关操作
### 注册用户接口
**URL:** https://fchat-api-dev.mini2436.xyz/user/register

**Type:** POST

**Author:** mini2436

**Content-Type:** application/json; charset=utf-8

**Description:** 注册用户接口

**Request-headers:**

Header | Type|Description|Required|Since
---|---|---|---|----
fchat-token|string|该值在登陆接口中返回,后续接口调用必传|true|-


**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
nickName|string|用户昵称|true|-
mobilePhone|string|手机号|true|-
email|string|邮箱|true|-
password|string|密码|true|-
avatar|string|头像|true|-
description|string|个人描述|false|-

**Request-example:**
```
curl -X POST -k -H 'Content-Type: application/json; charset=utf-8' -H 'fchat-token' -i https://fchat-api-dev.mini2436.xyz/user/register --data '{
  "nickName": "novella.purdy",
  "mobilePhone": "15711952059",
  "email": "弘文.夏@yahoo.com",
  "password": "1ezjvc",
  "avatar": "h6xdol",
  "description": "0xol8x"
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
code|int32|返回状态自定义code|-
msg|string|返回消息定义|-
data|object|返回请求体定义|-
└─token|string|登录成功的认证|-
└─userId|int64|主键|-
└─username|string|用户名|-
└─nickName|string|用户昵称|-
└─mobilePhone|string|手机号|-
└─email|string|邮箱|-
└─avatar|string|头像|-
└─birthday|string|出生日期|-

**Response-example:**
```
{
  "code": 497,
  "msg": "56baas",
  "data": {
    "token": "u8w57m",
    "userId": 404,
    "username": "天翊.胡",
    "nickName": "novella.purdy",
    "mobilePhone": "15711952059",
    "email": "弘文.夏@yahoo.com",
    "avatar": "fh359j",
    "birthday": "2021-09-29 16:53:48"
  }
}
```

### 系统账户登录
**URL:** https://fchat-api-dev.mini2436.xyz/user/

**Type:** POST

**Author:** mini2436

**Content-Type:** application/json; charset=utf-8

**Description:** 系统账户登录

**Request-headers:**

Header | Type|Description|Required|Since
---|---|---|---|----
fchat-token|string|该值在登陆接口中返回,后续接口调用必传|true|-


**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
mobilePhone|string|手机号|false|-
email|string|邮箱|false|-
password|string|密码|true|-
equipment|string|当前登录的设备类型|true|-

**Request-example:**
```
curl -X POST -k -H 'Content-Type: application/json; charset=utf-8' -H 'fchat-token' -i https://fchat-api-dev.mini2436.xyz/user/ --data '{
  "mobilePhone": "15711952059",
  "email": "弘文.夏@yahoo.com",
  "password": "0npji5",
  "equipment": "vz0jfz"
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
code|int32|返回状态自定义code|-
msg|string|返回消息定义|-
data|object|返回请求体定义|-
└─token|string|登录成功的认证|-
└─userId|int64|主键|-
└─username|string|用户名|-
└─nickName|string|用户昵称|-
└─mobilePhone|string|手机号|-
└─email|string|邮箱|-
└─avatar|string|头像|-
└─birthday|string|出生日期|-

**Response-example:**
```
{
  "code": 618,
  "msg": "q0g6u1",
  "data": {
    "token": "s6vsmk",
    "userId": 346,
    "username": "天翊.胡",
    "nickName": "novella.purdy",
    "mobilePhone": "15711952059",
    "email": "弘文.夏@yahoo.com",
    "avatar": "s9se03",
    "birthday": "2021-09-29 16:53:48"
  }
}
```

### 更新自己的用户数据
**URL:** https://fchat-api-dev.mini2436.xyz/user/

**Type:** PUT

**Author:** mini2436

**Content-Type:** application/json; charset=utf-8

**Description:** 更新自己的用户数据

**Request-headers:**

Header | Type|Description|Required|Since
---|---|---|---|----
fchat-token|string|该值在登陆接口中返回,后续接口调用必传|true|-


**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
nickName|string|用户昵称|false|-
mobilePhone|string|手机号|false|-
email|string|邮箱|false|-
password|string|密码|false|-
avatar|string|头像|false|-

**Request-example:**
```
curl -X PUT -k -H 'Content-Type: application/json; charset=utf-8' -H 'fchat-token' -i https://fchat-api-dev.mini2436.xyz/user/ --data '{
  "nickName": "novella.purdy",
  "mobilePhone": "15711952059",
  "email": "弘文.夏@yahoo.com",
  "password": "sxq9e4",
  "avatar": "msa6sq"
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
code|int32|返回状态自定义code|-
msg|string|返回消息定义|-
data|object|返回请求体定义|-
└─token|string|登录成功的认证|-
└─userId|int64|主键|-
└─username|string|用户名|-
└─nickName|string|用户昵称|-
└─mobilePhone|string|手机号|-
└─email|string|邮箱|-
└─avatar|string|头像|-
└─birthday|string|出生日期|-

**Response-example:**
```
{
  "code": 649,
  "msg": "etjzyo",
  "data": {
    "token": "46ky9w",
    "userId": 471,
    "username": "天翊.胡",
    "nickName": "novella.purdy",
    "mobilePhone": "15711952059",
    "email": "弘文.夏@yahoo.com",
    "avatar": "vn4tip",
    "birthday": "2021-09-29 16:53:48"
  }
}
```

### 根据当前用户的登录Token获取当前用户的信息
**URL:** https://fchat-api-dev.mini2436.xyz/user/

**Type:** GET

**Author:** mini2436

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 根据当前用户的登录Token获取当前用户的信息

**Request-headers:**

Header | Type|Description|Required|Since
---|---|---|---|----
fchat-token|string|该值在登陆接口中返回,后续接口调用必传|true|-


**Request-example:**
```
curl -X GET -k -H 'fchat-token' -i https://fchat-api-dev.mini2436.xyz/user/
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
code|int32|返回状态自定义code|-
msg|string|返回消息定义|-
data|object|返回请求体定义|-
└─token|string|登录成功的认证|-
└─userId|int64|主键|-
└─username|string|用户名|-
└─nickName|string|用户昵称|-
└─mobilePhone|string|手机号|-
└─email|string|邮箱|-
└─avatar|string|头像|-
└─birthday|string|出生日期|-

**Response-example:**
```
{
  "code": 967,
  "msg": "s7yykl",
  "data": {
    "token": "elle30",
    "userId": 117,
    "username": "天翊.胡",
    "nickName": "novella.purdy",
    "mobilePhone": "15711952059",
    "email": "弘文.夏@yahoo.com",
    "avatar": "w1m90b",
    "birthday": "2021-09-29 16:53:48"
  }
}
```

### 注销本次的账号登录
**URL:** https://fchat-api-dev.mini2436.xyz/user/logout

**Type:** PUT

**Author:** mini2436

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 注销本次的账号登录

**Request-headers:**

Header | Type|Description|Required|Since
---|---|---|---|----
fchat-token|string|该值在登陆接口中返回,后续接口调用必传|true|-


**Request-example:**
```
curl -X PUT -k -H 'fchat-token' -i https://fchat-api-dev.mini2436.xyz/user/logout
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
  "code": 717,
  "msg": "ag67pe",
  "data": "dtlb9a"
}
```


## 数据字典
### 系统请求状态码

Code |Type|Description
---|---|---
2000|int32|request success
4000|int32|request fail
5000|int32|server error
### 业务状态码

Code |Type|Description
---|---|---
5010|int32|数据异常
5020|int32|消息异常
5030|int32|参数异常
5040|int32|业务处理异常
5050|int32|系统异常
5060|int32|Token失效
5070|int32|当前请求发生错误
