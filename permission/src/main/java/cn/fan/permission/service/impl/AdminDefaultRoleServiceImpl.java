package cn.fan.permission.service.impl;


import cn.fan.permission.dao.AdminDefaultRoleMapper;
import cn.fan.permission.entity.AdminDefaultRole;
import cn.fan.permission.service.AdminDefaultRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户默认角色关联表关联用户表和角色表 服务实现类
 * </p>
 *
 * @author fan
 * @since 2020-11-23
 */
@Service
public class AdminDefaultRoleServiceImpl extends ServiceImpl<AdminDefaultRoleMapper, AdminDefaultRole> implements AdminDefaultRoleService {

}
