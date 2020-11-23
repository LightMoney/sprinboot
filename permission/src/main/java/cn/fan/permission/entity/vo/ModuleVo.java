package cn.fan.permission.entity.vo;

import cn.fan.permission.entity.Module;
import cn.fan.permission.entity.Permission;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhijian
 * @date 2019/01/08   0008
 * @description 模块值对象
 */
@Data
@ApiModel("ModuleVo 专门用于接受参数以及构建查询条件的类")
public class ModuleVo extends Module {

    /**
     * (权限，角色，模块关联表的主键id)
     */
    private String keyId;

    /**
     * (权限，角色，模块关联表的角色id)
     */
    private Integer roleId;

    /**
     * 权限id
     */
    private Integer permissionId;


    private Integer adminId;

    /**
     * 角色类型
     */
    private Integer roleType;

    /**
     * 员工类型
     */
    private Integer empId;

    /**
     * 岗位类型
     */
    private Integer postId;

    /**
     * 组织类型
     */
    private Integer organizeId;

    /**
     * 数据库表名
     */
    private String tableName;

    private boolean check;

    /**
     * 权限信息
     */
    @ApiModelProperty("模块权限集合")
    private List<Permission> permissionList = new ArrayList<>();

    /**
     * 权限信息
     */
    @ApiModelProperty("子模块集合")
    private List<ModuleVo> childModuleList = new ArrayList<>();

}