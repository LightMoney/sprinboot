package cn.fan.oauth2password.service;

import cn.fan.oauth2password.domain.SysRole;
import cn.fan.oauth2password.domain.SysUser;
import cn.fan.oauth2password.domain.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyUserDetailsService implements UserDetailsService {
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        //如果使用的密码已加密，这里就不加密
//        return new User(username, passwordEncoder.encode("123456"), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
//    }
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
//        Collection<GrantedAuthority> authorities = new ArrayList<>();
        List<SysRole> list = new ArrayList<>();
        //从数据库中获取用户信息
        SysUser sysUser = sysUserService.selectByName(userName);
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        //添加权限
        List<SysUserRole> sysUserRoles = sysUserRoleService.listByUserId(sysUser.getId());
//        sysUserRoles.forEach(v ->{
//            SysRole sysRole = sysRoleService.selectById(v.getRoleId());
//            authorities.add(new SimpleGrantedAuthority(sysRole.getAuthority()));
//        });
        sysUserRoles.forEach(v -> {
            SysRole sysRole = sysRoleService.selectById(v.getRoleId());
            list.add(sysRole);
        });
//返回UseDetails的实现类
        //        return new User(sysUser.getName(),passwordEncoder.encode(sysUser.getPassword()),authorities);
        //数据库存的加密过这里就不加密了
//        return new User(sysUser.getUsername(),sysUser.getPassword(),authorities);

        //这里实体类实现了security的内部实体类所以可以直接返回
        sysUser.setAuthorities(list);
        return sysUser;
    }
}
