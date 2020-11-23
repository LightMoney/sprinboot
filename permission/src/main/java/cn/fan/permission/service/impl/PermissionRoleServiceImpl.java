package cn.fan.permission.service.impl;


import cn.fan.permission.dao.PermissionRoleMapper;
import cn.fan.permission.entity.PermissionRole;
import cn.fan.permission.service.PermissionRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限关联表，关联角色，模块 服务实现类
 * </p>
 *
 * @author fan
 * @since 2020-11-23
 */
@Service
public class PermissionRoleServiceImpl extends ServiceImpl<PermissionRoleMapper, PermissionRole> implements PermissionRoleService {

}
