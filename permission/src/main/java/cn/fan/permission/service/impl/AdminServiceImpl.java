package cn.fan.permission.service.impl;


import cn.fan.permission.dao.AdminMapper;
import cn.fan.permission.entity.Admin;
import cn.fan.permission.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表包含用户基本信息 服务实现类
 * </p>
 *
 * @author fan
 * @since 2020-11-23
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
