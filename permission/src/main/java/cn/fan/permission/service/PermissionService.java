package cn.fan.permission.service;


import cn.fan.permission.entity.Permission;
import cn.fan.permission.entity.vo.ModuleVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 此表为权限表 服务类
 * </p>
 *
 * @author fan
 * @since 2020-11-23
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 根据模块类型和用户id查询模块权限树(用户涉及角色 用户默认角色 用户组织岗位角色  为用户赋予的角色)
     * @param moduleType 模块类型
     * @param adminId 用户id
     * @return 模块权限树
     */
    List<ModuleVo> queryModPerByTypeAndAdminId(Integer moduleType, Integer adminId);

    /**
     * 剔除毫无权限的模块
     * @param vos 模块集合
     * @return  返回剔除后的模块树
     */
    public List<ModuleVo> excludeModule(List<ModuleVo> vos);

}
