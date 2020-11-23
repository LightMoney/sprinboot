package cn.fan.permission.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
@ApiModel("用户表")
@TableName("t_bd_admin")
public class Admin implements Serializable {

    @TableId(value = "admin_id",type = IdType.AUTO)// 需要指定，否则无法新增后拿到回调的id，以及进行删除等操作
    @ApiModelProperty(value = "用户ID")
    private Integer adminId;

    @ApiModelProperty(value = "企业ID")
    private Integer enterpriseId;

    @ApiModelProperty(value = "用户对外id（生成后不能修改）")
    private String adminExternalId;

    @ApiModelProperty(value = "员工或者客户或者供应商关联关系id")
    private Integer relationId;

    @ApiModelProperty(value = "用户来源")
    private Integer adminOriginId;

    @ApiModelProperty(value = "用户名字")
    private String adminName;

    @ApiModelProperty(value = "用户编码")
    private String adminCode;

    @ApiModelProperty(value = "用户密码")
    private String adminPassword;

    @ApiModelProperty(value = "电话号码")
    private String telephone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人id")
    private Integer createUserId;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "修改人id")
    private Integer modifyUserId;

    @ApiModelProperty(value = "头像")
    private String figureurl;

    @ApiModelProperty(value = "头像1")
    private String figureurl1;

    @ApiModelProperty(value = "数据状态(1:审核中（默认）2：审核未通过 3：已审核）")
    private Integer dataStatus;

    @ApiModelProperty(value = "审核失败原因")
    private String failureDescribe;

    @ApiModelProperty(value = "删除状态0：已删除1：未删除(默认未删除）")
    private Integer deleteStatus;

    @ApiModelProperty(value = "是否可用 默认值：0：不可用 1：可用  默认可用")
    private Integer enabled;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "用户自定义")
    private String userDefine;

    @ApiModelProperty(value = "是否授权协议")
    private Integer warrant;

    private static final long serialVersionUID = 1L;

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getAdminExternalId() {
        return adminExternalId;
    }

    public void setAdminExternalId(String adminExternalId) {
        this.adminExternalId = adminExternalId == null ? null : adminExternalId.trim();
    }

    public Integer getRelationId() {
        return relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    public Integer getAdminOriginId() {
        return adminOriginId;
    }

    public void setAdminOriginId(Integer adminOriginId) {
        this.adminOriginId = adminOriginId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName == null ? null : adminName.trim();
    }

    public String getAdminCode() {
        return adminCode;
    }

    public void setAdminCode(String adminCode) {
        this.adminCode = adminCode == null ? null : adminCode.trim();
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword == null ? null : adminPassword.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
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

    public String getFigureurl() {
        return figureurl;
    }

    public void setFigureurl(String figureurl) {
        this.figureurl = figureurl == null ? null : figureurl.trim();
    }

    public String getFigureurl1() {
        return figureurl1;
    }

    public void setFigureurl1(String figureurl1) {
        this.figureurl1 = figureurl1 == null ? null : figureurl1.trim();
    }

    public Integer getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(Integer dataStatus) {
        this.dataStatus = dataStatus;
    }

    public String getFailureDescribe() {
        return failureDescribe;
    }

    public void setFailureDescribe(String failureDescribe) {
        this.failureDescribe = failureDescribe == null ? null : failureDescribe.trim();
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getUserDefine() {
        return userDefine;
    }

    public void setUserDefine(String userDefine) {
        this.userDefine = userDefine == null ? null : userDefine.trim();
    }

    public Integer getWarrant() {
        return warrant;
    }

    public void setWarrant(Integer warrant) {
        this.warrant = warrant;
    }
}