package cn.fan.permission.dao;


import cn.fan.permission.entity.Module;
import cn.fan.permission.entity.vo.ModuleVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 模块表包含模块基本信息 Mapper 接口
 * </p>
 *
 * @author fan
 * @since 2020-11-23
 */
public interface ModuleMapper extends BaseMapper<Module> {
    /**
     * 获取子模块
     *
     * @param map
     * @return
     */
    List<ModuleVo> queryChildModule(Map map);

    /**
     * 动态查询员工，岗位，组织的角色id（多岗位，多组织...）
     *
     * @param map
     * @return
     */
    List<Integer> queryRoleIdDynamic0(Map map);
}
