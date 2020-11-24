package  cn.fan.permission.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel("权限")
@Data
public class Permission implements Cloneable, Serializable {
    @ApiModelProperty("主键")
    private Integer permissionId;
    @ApiModelProperty("权限名称")
    private String permissionName;
    @ApiModelProperty("预留扩展空间，可以加逗号隔开，多个路径匹配")
    private String permissionOrm;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("创建人id")
    private Integer createUserId;
    @ApiModelProperty("删除状态0：已删除1：未删除(默认未删除）")
    private Integer deleteStatus;
    @ApiModelProperty("是否可用")
    private Integer enabled;
    @ApiModelProperty("是否选中")
    private boolean check;
    @ApiModelProperty("权限字")
    private String keyWord;

    @Override
    public String toString(){
        return permissionId.intValue() + "-" + permissionName;
    }

    @Override
    public int hashCode(){
        return permissionId.hashCode();
    }

    @Override
    public boolean equals(Object p1) {
        if (!(p1 instanceof Permission)) {
            return false;
        }
        Permission p = (Permission) p1;
        return this.permissionId.equals(p.permissionId);
    }

    @Override
    public Permission clone() {
        Permission permission = null;
        try {
            permission = (Permission) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return permission;
    }

}