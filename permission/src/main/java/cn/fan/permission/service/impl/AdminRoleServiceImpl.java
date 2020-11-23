package cn.fan.permission.service.impl;


import cn.fan.permission.dao.AdminRoleMapper;
import cn.fan.permission.entity.AdminRole;
import cn.fan.permission.service.AdminRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色关联表关联用户表和角色表 服务实现类
 * </p>
 *
 * @author fan
 * @since 2020-11-23
 */
@Service
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole> implements AdminRoleService {

}
