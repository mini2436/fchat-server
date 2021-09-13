package xyz.mini2436.fchat.constant;

/**
 * 系统常量定义
 *
 * @author mini2436
 * @date 2021-07-30 09:48
 **/
public interface SystemConstant {

    /**
     * 好友申请状态定义
     */
    interface FriendApplyStatus{
        /**
         * 待审核
         */
        Integer WAIT = 0;
        /**
         * 通过
         */
        Integer PASS = 1;
        /**
         * 拒绝
         */
        Integer REJECT = 2;

    }

    /**
     * 数据库删除状态常量
     */
    interface DataBaseDelStatus{
        /**
         * 未删除
          */
        Integer NOT_DELETE = 0;
        /**
         * 已删除
         */
        Integer DELETE = 1;
    }

}
