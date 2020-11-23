package cn.fan.permission.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
@TableName("t_bd_module_type")
public class ModuleType implements Serializable {
    @ApiModelProperty(value = "模块来源类型ID")
    private Integer moduleType;

    @ApiModelProperty(value = "模块来源类型名字")
    private String typeOriginName;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人id")
    private String createUser;

    @ApiModelProperty(value = "删除状态0：已删除1：未删除(默认未删除）")
    private Integer deleteStatus;

    @ApiModelProperty(value = "是否可用 默认值：0：不可用 1：可用  默认可用")
    private Integer enabled;

    @ApiModelProperty(value = "是否固定 1表示固定 0表示不固定 固定后不允许修改")
    private Integer isfixed;

    private static final long serialVersionUID = 1L;

    public Integer getModuleType() {
        return moduleType;
    }

    public void setModuleType(Integer moduleType) {
        this.moduleType = moduleType;
    }

    public String getTypeOriginName() {
        return typeOriginName;
    }

    public void setTypeOriginName(String typeOriginName) {
        this.typeOriginName = typeOriginName == null ? null : typeOriginName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Integer getIsfixed() {
        return isfixed;
    }

    public void setIsfixed(Integer isfixed) {
        this.isfixed = isfixed;
    }
}