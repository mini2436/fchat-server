# fchat数据库文档

**数据库名：** fchat

**文档版本：** 0.0.1-dev

**文档描述：** 该文档仅限用于开发参考，实际开发请以数据库为准

| 表名                  | 说明       |
| :---: | :---: |
| [fchat_dynamic](#fchat_dynamic) | 用户动态表 |
| [fchat_extra_user](#fchat_extra_user) | 三方登录用户基础数据 |
| [fchat_friend](#fchat_friend) | 好友表 |
| [fchat_friend_apply](#fchat_friend_apply) | 好友申请列表数据表 |
| [fchat_group](#fchat_group) | 分组表 |
| [fchat_group_user](#fchat_group_user) | 分组用户表 |
| [fchat_user](#fchat_user) | 用户表 |
| [test_table](#test_table) |  |

**表名：** <a id="fchat_dynamic">fchat_dynamic</a>

**说明：** 用户动态表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | dynamic_id |   bigint   | 20 |   0    |    N     |  Y   |       | 动态表主键  |
|  2   | user_id |   int   | 10 |   0    |    N     |  N   |       | 动态发送用户  |
|  3   | title |   varchar   | 32 |   0    |    N     |  N   |       | 动态标题  |
|  4   | content |   varchar   | 255 |   0    |    N     |  N   |       | 动态内容  |
|  5   | img |   varchar   | 32 |   0    |    N     |  N   |       | 动态图片  |
|  6   | create_time |   bigint   | 20 |   0    |    N     |  N   |       | 动态创建时间  |
|  7   | send_time |   bigint   | 20 |   0    |    N     |  N   |       | 动态发布时间  |
|  8   | send_status |   char   | 1 |   0    |    N     |  N   |       | 发布状态(0未发布1已发布)  |
|  9   | del_status |   char   | 1 |   0    |    N     |  N   |       | 删除状态(0未删除,1已删除)  |

**表名：** <a id="fchat_extra_user">fchat_extra_user</a>

**说明：** 三方登录用户基础数据

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   bigint   | 20 |   0    |    N     |  Y   |       | 主键  |
|  2   | open_id |   varchar   | 64 |   0    |    N     |  N   |       | 三方登录凭证  |
|  3   | user_id |   bigint   | 20 |   0    |    N     |  N   |       | 用户id  |
|  4   | type |   varchar   | 32 |   0    |    N     |  N   |       | 三方认证类型  |
|  5   | type_name |   varchar   | 255 |   0    |    Y     |  N   |       | 用户名称  |

**表名：** <a id="fchat_friend">fchat_friend</a>

**说明：** 好友表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   bigint   | 20 |   0    |    N     |  Y   |       | 主键  |
|  2   | user_id |   bigint   | 20 |   0    |    N     |  N   |       | 用户id  |
|  3   | friend |   bigint   | 20 |   0    |    N     |  N   |       | 好友id  |
|  4   | created_time |   bigint   | 20 |   0    |    Y     |  N   |       | 创建时间  |

**表名：** <a id="fchat_friend_apply">fchat_friend_apply</a>

**说明：** 好友申请列表数据表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   bigint   | 20 |   0    |    N     |  Y   |       | 主键  |
|  2   | apply_user_id |   bigint   | 20 |   0    |    N     |  N   |       | 申请的用户ID  |
|  3   | friend_user_id |   bigint   | 20 |   0    |    N     |  N   |       | 添加的用户ID  |
|  4   | apply_content |   varchar   | 255 |   0    |    N     |  N   |       | 申请好友的添加信息  |
|  5   | apply_time |   bigint   | 20 |   0    |    N     |  N   |       | 申请时间  |
|  6   | apply_status |   char   | 1 |   0    |    N     |  N   |       | 申请状态(0待审核,1,通过,2拒绝)  |
|  7   | del_status |   char   | 1 |   0    |    N     |  N   |   0    | 删除状态(0未删除1已删除)  |

**表名：** <a id="fchat_group">fchat_group</a>

**说明：** 分组表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | group_id |   bigint   | 20 |   0    |    N     |  Y   |       | 分组ID  |
|  2   | group_name |   varchar   | 32 |   0    |    N     |  N   |       | 组名称  |
|  3   | group_desc |   varchar   | 255 |   0    |    N     |  N   |       | 分组描述  |
|  4   | group_img |   varchar   | 32 |   0    |    N     |  N   |       | 组头像  |
|  5   | create_user_id |   bigint   | 20 |   0    |    N     |  N   |       | 分组创建人  |
|  6   | admin_user_id |   bigint   | 20 |   0    |    N     |  N   |       | 管理员ID  |
|  7   | created_time |   bigint   | 20 |   0    |    N     |  N   |       | 创建时间  |
|  8   | updated_time |   bigint   | 20 |   0    |    Y     |  N   |       | 更新时间  |

**表名：** <a id="fchat_group_user">fchat_group_user</a>

**说明：** 分组用户表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   bigint   | 20 |   0    |    N     |  Y   |       | 主键ID  |
|  2   | group_id |   bigint   | 20 |   0    |    N     |  N   |       | 分组主键  |
|  3   | user_id |   bigint   | 20 |   0    |    N     |  N   |       | 用户ID  |
|  4   | create_time |   bigint   | 20 |   0    |    N     |  N   |       | 创建时间  |

**表名：** <a id="fchat_user">fchat_user</a>

**说明：** 用户表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | user_id |   bigint   | 20 |   0    |    N     |  Y   |       | 主键  |
|  2   | nick_name |   varchar   | 32 |   0    |    N     |  N   |       | 用户昵称  |
|  3   | mobile_phone |   varchar   | 32 |   0    |    Y     |  N   |       | 手机号  |
|  4   | email |   varchar   | 32 |   0    |    Y     |  N   |       | 邮箱  |
|  5   | password |   varchar   | 64 |   0    |    Y     |  N   |       | 密码  |
|  6   | avatar |   varchar   | 255 |   0    |    Y     |  N   |       | 头像  |
|  7   | description |   varchar   | 255 |   0    |    Y     |  N   |       | 个性签名  |
|  8   | created_time |   bigint   | 20 |   0    |    N     |  N   |       | 创建时间  |
|  9   | updated_time |   bigint   | 20 |   0    |    Y     |  N   |       | 更新时间  |
|  10   | del_status |   char   | 1 |   0    |    N     |  N   |   0    | 逻辑删除  |

**表名：** <a id="test_table">test_table</a>

**说明：** 

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   bigint   | 20 |   0    |    N     |  Y   |       | 主键  |
|  2   | name |   varchar   | 50 |   0    |    Y     |  N   |       | 测试名称  |
|  3   | phone |   varchar   | 11 |   0    |    Y     |  N   |       | 手机  |
|  4   | age |   int   | 10 |   0    |    Y     |  N   |       | 年龄  |
