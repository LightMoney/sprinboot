package cn.fan.permission.entity;

public class PermissionConsts {

    /**
     * 正数-父模块的权限id(由于父模块只是用于展示并没有实质权限固化为某个数字)
     */
    public static final Integer PARENT_MODULE_PERMISSION = 999;
    /**
     * 负数-父模块的权限id
     */
    public static final Integer NEGATIVE_PARENT_MODULE_PERMISSION = -999;

    /** redis 中存放用户权限的key */
    public static final String REDIS_ADMIN_PERMISSION_PRE = "adminPermission";

    /** 权限可用即为1 */
    public static final int PERMISSION_ENABLE = 1;

}