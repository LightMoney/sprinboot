package cn.fan.permission.service.impl;


import cn.fan.permission.dao.ModuleMapper;
import cn.fan.permission.dao.ModulePermissionMapper;
import cn.fan.permission.dao.PermissionMapper;
import cn.fan.permission.dao.RoleMapper;
import cn.fan.permission.entity.Permission;
import cn.fan.permission.entity.PermissionConsts;
import cn.fan.permission.entity.vo.ModuleVo;
import cn.fan.permission.service.PermissionService;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 此表为权限表 服务实现类
 * </p>
 *
 * @author fan
 * @since 2020-11-23
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private  PermissionMapper permissionMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private ModulePermissionMapper modulePermissionMapper;

    @Override
    public List<ModuleVo> queryModPerByTypeAndAdminId(Integer moduleType, Integer adminId) {
        //根据用户id动态路由用户默认id角色表
        if (null == adminId) {
            return null;
        }
        List<ModuleVo> vos = queryModperByType(moduleType);
        if (CollectionUtils.isEmpty(vos)) {
            return vos;
        }
        List<Integer> roleIds = new ArrayList<>();
//        获取一个角色并集
        //这里是默认角色
        roleIds.addAll(getAdminDefaultRoleId(adminId));
//        用户角色
        roleIds.addAll(roleMapper.queryRoleIdByAdminId(adminId));
//        员工角色
//        roleIds.addAll(getRoleIdListFaceEmpType(adminId));

        if (CollectionUtils.isEmpty(roleIds)) {
            log.warn("无用户id参数，无法查询用户的权限树");
            return vos;
        }
        buildModuleTreeCheck(vos, buildMoPerMap(permissionMapper.getPerRoleByRoleId(roleIds)));
        buildModuleTreeEnable(vos, getAllModPer());
        return vos;
    }

    /**
     * 剔除毫无权限的模块
     *
     * @param vos 模块集合
     * @return 返回剔除后的模块树
     */
    @Override
    public List<ModuleVo> excludeModule(List<ModuleVo> vos) {
        List<ModuleVo> res = new ArrayList<>();
        if (CollectionUtils.isEmpty(vos)) {
            return res;
        }
        for (ModuleVo vo : vos) {
            //相当于父模块
            if (CollectionUtils.isNotEmpty(vo.getChildModuleList())) {
                List<ModuleVo> child = excludeModule(vo.getChildModuleList());
                if (CollectionUtils.isEmpty(child)) {
                    continue;
                }
                vo.setChildModuleList(child);
                res.add(vo);
            } else {
                //最底层的子模块
                List<Permission> permissions = vo.getPermissionList();
                if (CollectionUtils.isNotEmpty(permissions)) {
                    for (Permission permission : permissions) {
                        if (permission.isCheck() && permission.getEnabled() != null && permission.getEnabled() == PermissionConsts.PERMISSION_ENABLE) {
                            res.add(vo);
                            break;
                        }
                    }
                }
            }
        }
        return res;
    }

    /**
     * 通过模块类型获取模块权限对应关系
     *
     * @param moduleType 模块类型 空返回所有的   有条件则进行条件查询
     * @return 数据集合
     */
    private List<ModuleVo> queryModperByType(Integer moduleType) {
        Map<String, Object> params = new HashMap<>();
        params.put("deleteStatus", 1);
        params.put("moduleType", moduleType);
        List<ModuleVo> modules = moduleMapper.queryChildModule(params);
        List<Permission> permissions = permissionMapper.queryAllPermission(1);
        List<ModuleVo> moduleVos = buildModuleTree(modules, permissions, -1);
        return moduleVos;
    }

    /**
     * @return 模块集合
     * @Description 构建模块树，并且填充权限
     * @Param modules 模块 permissions 权限  parentId 父id
     **/
    private List<ModuleVo> buildModuleTree(List<ModuleVo> modules, List<Permission> permissions, Integer parentId) {
        List<ModuleVo> childModule = new ArrayList<>();
        if (org.apache.commons.collections.CollectionUtils.isEmpty(modules) || parentId == null) {
            return childModule;
        }
        for (ModuleVo vo : modules) {
            if (vo.getParentId() != null && vo.getParentId().equals(parentId)) {
                vo.setPermissionList(deepCopy(permissions));
                childModule.add(vo);
                vo.getChildModuleList().addAll(buildModuleTree(modules, permissions, vo.getModuleId()));
            }
        }
        return childModule;
    }

    //permission 实现Cloneable, Serializable 覆写clone方法
    private List<Permission> deepCopy(List<Permission> permissions) {
        if (org.apache.commons.collections.CollectionUtils.isEmpty(permissions)) {
            return null;
        }
        List<Permission> result = new ArrayList<>();
        for (Permission permission : permissions) {
            result.add(permission.clone());
        }
        return result;
    }

    /**
     * @return void 无
     * @Description 构造模块权限树的选中状态
     * @Param [vos, moPerMap] 模块   拥有的模块权限
     **/
    private void buildModuleTreeCheck(List<ModuleVo> vos, Map<Integer, Set<Integer>> moPerMap) {
        if (CollectionUtils.isEmpty(vos) || MapUtils.isEmpty(moPerMap)) {
            return;
        }
        for (ModuleVo vo : vos) {
            if (CollectionUtils.isNotEmpty(vo.getChildModuleList())) {
                buildModuleTreeCheck(vo.getChildModuleList(), moPerMap);
            }
            if (!moPerMap.containsKey(vo.getModuleId()) || CollectionUtils.isEmpty(vo.getPermissionList())) {
                continue;
            }
            //判断父模块的是否要选中非权限
            if (moPerMap.containsKey(vo.getModuleId()) && moPerMap.get(vo.getModuleId()).contains(PermissionConsts.PARENT_MODULE_PERMISSION) && !moPerMap.get(vo.getModuleId()).contains(PermissionConsts.NEGATIVE_PARENT_MODULE_PERMISSION)) {
                vo.setCheck(true);
            }
            for (Permission permission : vo.getPermissionList()) {
                if (moPerMap.get(vo.getModuleId()).contains(permission.getPermissionId()) && !moPerMap.get(vo.getModuleId()).contains(-permission.getPermissionId())) {
                    permission.setCheck(true);
                }
            }
        }
    }

    /**
     * 构建权限的可用性
     * @param vos 权限树
     * @param moPerMap 模块权限对应关系
     */
    private void buildModuleTreeEnable(List<ModuleVo> vos, Map<Integer, Set<Integer>> moPerMap) {
        if (CollectionUtils.isEmpty(vos) || MapUtils.isEmpty(moPerMap)) {
            return;
        }
        for (ModuleVo vo : vos) {
            if (CollectionUtils.isNotEmpty(vo.getChildModuleList())) {
                buildModuleTreeEnable(vo.getChildModuleList(), moPerMap);
            }
            for (Permission permission : vo.getPermissionList()) {
                if (moPerMap.get(vo.getModuleId()) == null ||
                        !moPerMap.get(vo.getModuleId()).contains(permission.getPermissionId())) {
                    permission.setEnabled(0);
                }
            }
        }
    }


    /**
     * @return java.util.Map<java.lang.Integer   ,   java.util.Set   <   java.lang.Integer>> 模块-权限集合
     * @Description 构造模块权限对应关系
     * @Param [rs] 查询到的数据行
     **/
    private Map<Integer, Set<Integer>> buildMoPerMap(List<Map<String, Integer>> rs) {
        if(org.apache.commons.collections.CollectionUtils.isEmpty(rs)){
            return null;
        }
        Map<Integer, Set<Integer>> moPerMap = new HashMap<>();
        for (Map<String, Integer> map : rs) {
            Integer moduleId = map.get("moduleId");
            Integer permissionId = map.get("permissionId");
            if (null == moduleId || null == permissionId) {
                continue;
            }
            if (moPerMap.containsKey(moduleId)) {
                moPerMap.get(moduleId).add(permissionId);
            } else {
                Set<Integer> set = new HashSet<>();
                set.add(permissionId);
                moPerMap.put(moduleId, set);
            }
        }
        return moPerMap;
    }
    /**
     * 获取所有模块可以有的权限
     * @return Map<Integer,Set<Integer>>
     */
    private Map<Integer,Set<Integer>>  getAllModPer(){
        List<Map<String,Integer>> modPers = modulePermissionMapper.getAllModulePermission();
        if(org.apache.commons.collections.CollectionUtils.isEmpty(modPers)){
            return null;
        }
        return buildMoPerMap(modPers);
    }

    /**
     * 通过用户id获取用户默认角色
     * @param adminId 用户id
     * @return 角色id
     */
    private List<Integer> getAdminDefaultRoleId(Integer adminId) {
        List<Integer> adminIds = new ArrayList<>();
        adminIds.add(adminId);
        List<Integer> adminRoleIds = getRoleIdDynamic("t_bd_admin_default_role", "adminIdList", adminIds);
        // 追加
        return adminRoleIds;
    }

    private List<Integer> getRoleIdDynamic(String tableName, String key, List<Integer> value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        map.put("tableName", tableName.trim());
        return moduleMapper.queryRoleIdDynamic0(map);
    }
}
