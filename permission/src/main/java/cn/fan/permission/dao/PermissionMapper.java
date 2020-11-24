package cn.fan.permission.dao;


import cn.fan.permission.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 此表为权限表 Mapper 接口
 * </p>
 *
 * @author fan
 * @since 2020-11-23
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * @Description  通过角色id获取权限模块对应关系
     * @Param [roleId] 角色id
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Integer>>
     **/
    List<Map<String,Integer>> getPerRoleByRoleId(@Param("roleIds") List<Integer> roleIds);


    /**
     * 查询所有权限
     *
     * @param deleteStatus
     * @return
     */
    List<Permission> queryAllPermission(Integer deleteStatus);
}
