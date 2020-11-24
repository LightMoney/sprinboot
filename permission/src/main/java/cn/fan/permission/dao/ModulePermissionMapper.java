package cn.fan.permission.dao;


import cn.fan.permission.entity.ModulePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fan
 * @since 2020-11-23
 */
public interface ModulePermissionMapper extends BaseMapper<ModulePermission> {
    List<Map<String, Integer>> getAllModulePermission();

    List<Map<String, String>> getAllPermissionUrl();
}
