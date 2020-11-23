package cn.fan.permission.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
@TableName()
public class ModulePermission implements Serializable {
    @ApiModelProperty(value = "模块id")
    private Integer nModuleId;

    @ApiModelProperty(value = "权限id")
    private Integer nPermissionId;

    private static final long serialVersionUID = 1L;

    public Integer getnModuleId() {
        return nModuleId;
    }

    public void setnModuleId(Integer nModuleId) {
        this.nModuleId = nModuleId;
    }

    public Integer getnPermissionId() {
        return nPermissionId;
    }

    public void setnPermissionId(Integer nPermissionId) {
        this.nPermissionId = nPermissionId;
    }
}