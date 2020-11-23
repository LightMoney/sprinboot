package cn.fan.permission.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
@ApiModel("模块实体类")
@TableName("t_bd_module")
public class Module implements Serializable {

    @TableId(value = "module_id",type = IdType.AUTO)
    private Integer moduleId;

    @ApiModelProperty(value = "模块名字")
    private String moduleName;

    @ApiModelProperty(value = "模块URL")
    private String moduleUrl;

    @ApiModelProperty(value = "权限字")
    private String keyword;

    @ApiModelProperty(value = "前端需要的属性")
    private String flag;

    @ApiModelProperty(value = "模块类型")
    private Integer moduleType;

    @ApiModelProperty(value = "模块父ID（-1表示是父模块）")
    private Integer parentId;

    @ApiModelProperty(value = "模块序号")
    private Integer orderNo;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private Integer createUserId;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "修改人")
    private Integer modifyUserId;

    @ApiModelProperty(value = "图像路径")
    private String imagePath;

    @ApiModelProperty(value = "用户自定义")
    private String userDefine;

    @ApiModelProperty(value = "删除状态0：已删除1：未删除(默认未删除）")
    private Integer deleteStatus;

    @ApiModelProperty(value = "是否支持报表，默认不支持0 支持为1")
    private Integer supportReport;

    @ApiModelProperty(value = "是否可用 默认值：0：不可用 1：可用  默认可用")
    private Integer enabled;

    private static final long serialVersionUID = 1L;

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName == null ? null : moduleName.trim();
    }

    public String getModuleUrl() {
        return moduleUrl;
    }

    public void setModuleUrl(String moduleUrl) {
        this.moduleUrl = moduleUrl == null ? null : moduleUrl.trim();
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public Integer getModuleType() {
        return moduleType;
    }

    public void setModuleType(Integer moduleType) {
        this.moduleType = moduleType;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(Integer modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath == null ? null : imagePath.trim();
    }

    public String getUserDefine() {
        return userDefine;
    }

    public void setUserDefine(String userDefine) {
        this.userDefine = userDefine == null ? null : userDefine.trim();
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Integer getSupportReport() {
        return supportReport;
    }

    public void setSupportReport(Integer supportReport) {
        this.supportReport = supportReport;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }
}