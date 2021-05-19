package cn.fan.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import sun.util.resources.cldr.om.CalendarData_om_KE;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义的realm相当于认证授权处理器
 */
public class PermissonRealm extends AuthorizingRealm {
    /**
     * 自定义realm名称
     *
     * @param name
     */
    @Override
    public void setName(String name) {
        super.setName("PermissonRealm");

    }

    //    授权:根据认证的信息获取到用户的角色权限
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String username = (String) principalCollection.getPrimaryPrincipal();
//        权限和角色都是查出来的这里模拟
        List<String> perms = new ArrayList<String>();
        perms.add("user:find");
        List<String> roles = new ArrayList<String>();
        roles.add("role1");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(roles);
        simpleAuthorizationInfo.addStringPermissions(perms);
        return simpleAuthorizationInfo;
    }

    //认证 ：比较密码
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        String password = new String(token.getPassword());
        if ("1234".equals(password)) {
            return new SimpleAuthenticationInfo(username, password, getName());//用户名，密码，realm域名称
        } else {
            throw new RuntimeException("密码错误");
        }

    }
}
