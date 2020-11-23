package cn.fan.permission.service.impl;


import cn.fan.permission.dao.PermissionMapper;
import cn.fan.permission.entity.Permission;
import cn.fan.permission.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
