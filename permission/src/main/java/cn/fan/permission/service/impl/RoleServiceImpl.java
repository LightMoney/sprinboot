package cn.fan.permission.service.impl;


import cn.fan.permission.dao.RoleMapper;
import cn.fan.permission.entity.Role;
import cn.fan.permission.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表包括角色基本信息 服务实现类
 * </p>
 *
 * @author fan
 * @since 2020-11-23
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
