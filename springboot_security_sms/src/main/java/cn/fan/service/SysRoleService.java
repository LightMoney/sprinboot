package cn.fan.service;

import cn.fan.dao.SysRoleMapper;
import cn.fan.domain.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleService {
    @Autowired
    private SysRoleMapper roleMapper;

    public SysRole selectById(Integer id){
        return roleMapper.selectById(id);
    }

    public SysRole selectByName(String name) {
        return roleMapper.selectByName(name);
    }
}
