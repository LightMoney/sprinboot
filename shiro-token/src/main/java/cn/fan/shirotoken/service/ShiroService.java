package cn.fan.shirotoken.service;

import cn.fan.shirotoken.entity.SysToken;
import cn.fan.shirotoken.entity.User;

import java.util.Map;

/**
 * TODO
 *
 * @author fgy
 * @version 1.0
 * @date 2020/12/18 15:18
 */
public interface ShiroService {

     User findByUsername(String username);
     Map<String, Object> createToken(Integer userId);
     void logout(String token);
     SysToken findByToken(String accessToken);
     User findByUserId(Integer userId);
}
