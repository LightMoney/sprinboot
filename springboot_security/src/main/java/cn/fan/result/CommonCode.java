package cn.fan.result;

import lombok.ToString;

/**
 * @Author: wuchen.
 * @Description:
 * @Date:Created in 2019/1/10 18:33.
 * @Modified By:
 */

@ToString
public enum CommonCode implements ResultCode {

    SUCCESS(true, 200, "操作成功！"),
    FAIL(false, 500, "操作失败！"),
    UNAUTHENTICATED(false, 10001, "此操作需要登陆系统！"),
    UNAUTHORISE(false, 10002, "权限不足，无权操作！"),
    EXIST_LEVEL_TASK(false, 10003, "操作失败,该任务下已有下级任务,不能修改或删除！"),
    SERVER_ERROR(false, 99999, "抱歉，系统繁忙，请稍后重试！");
    //    private static ImmutableMap<Integer, CommonCode> codes ;
    //操作是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;

    private CommonCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }


}