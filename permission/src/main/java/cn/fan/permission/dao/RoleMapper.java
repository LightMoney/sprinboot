package cn.fan.permission.dao;


import cn.fan.permission.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色表包括角色基本信息 Mapper 接口
 * </p>
 *
 * @author fan
 * @since 2020-11-23
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 通过用户id查找角色id
     *
     * @param adminId
     * @return
     */
    List<Integer> queryRoleIdByAdminId(Integer adminId);

}
