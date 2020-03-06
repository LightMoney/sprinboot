package cn.fan.service;

import cn.fan.domain.SysRole;
import cn.fan.domain.SysUser;
import cn.fan.domain.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Auth Mr.fan
 * Date 2020/1/15 13:55
 **/
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService
{
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        //从数据库中获取用户信息
        SysUser sysUser = sysUserService.selectByName(userName);
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        //添加权限
        List<SysUserRole> sysUserRoles = sysUserRoleService.listByUserId(sysUser.getId());
        sysUserRoles.forEach(v ->{
            SysRole sysRole = sysRoleService.selectById(v.getRoleId());
            authorities.add(new SimpleGrantedAuthority(sysRole.getName()));
        });

//返回UseDetails的实现类
        //        return new User(sysUser.getName(),passwordEncoder.encode(sysUser.getPassword()),authorities);
        //数据库存的加密过这里就不加密了
        return new User(sysUser.getName(),sysUser.getPassword(),authorities);
    }
}
