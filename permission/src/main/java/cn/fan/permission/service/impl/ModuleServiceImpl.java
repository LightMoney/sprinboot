package cn.fan.permission.service.impl;


import cn.fan.permission.dao.ModuleMapper;
import cn.fan.permission.entity.Module;
import cn.fan.permission.service.ModuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 模块表包含模块基本信息 服务实现类
 * </p>
 *
 * @author fan
 * @since 2020-11-23
 */
@Service
public class ModuleServiceImpl extends ServiceImpl<ModuleMapper, Module> implements ModuleService {

}
