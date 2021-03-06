package cn.fan.shirotoken.service.impl;

import cn.fan.shirotoken.dao.SysTokenRepository;
import cn.fan.shirotoken.dao.UserRepository;
import cn.fan.shirotoken.entity.SysToken;
import cn.fan.shirotoken.entity.User;
import cn.fan.shirotoken.service.ShiroService;
import cn.fan.shirotoken.utils.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 大誌
 * @Date 2019/3/30 22:18
 * @Version 1.0
 */
@Service
public class ShiroServiceImpl implements ShiroService {
 
 
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SysTokenRepository sysTokenRepository;
 
    /**
     * 根据username查找用户
     *
     * @param username
     * @return User
     */
    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }
 
    //12小时后过期
    private final static int EXPIRE = 3600 * 12;
 
    @Override
    /**
     * 生成一个token
     *@param  [userId]
     *@return Result
     */
    public Map<String, Object> createToken(Integer userId) {
        Map<String, Object> result = new HashMap<>();
        //生成一个token
        String token = TokenGenerator.generateValue();
        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);
        //判断是否生成过token
        SysToken tokenEntity = sysTokenRepository.findByUserId(userId);
        if (tokenEntity == null) {
            tokenEntity = new SysToken();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);
            //保存token
            sysTokenRepository.save(tokenEntity);
        } else {
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);
            //更新token
            sysTokenRepository.save(tokenEntity);
        }
        result.put("token", token);
        result.put("expire", EXPIRE);
        return result;
    }
    /**
     * 更新数据库的token，使前端拥有的token失效
     * 防止黑客利用token搞事情
     *
     * @param token
     */
    @Override
    public void logout(String token) {
        SysToken byToken = findByToken(token);
        //生成一个token
        token = TokenGenerator.generateValue();
        //修改token
        byToken.setToken(token);
        //使前端获取到的token失效
        sysTokenRepository.save(byToken);
    }
 
    @Override
    public SysToken findByToken(String accessToken) {
        return sysTokenRepository.findByToken(accessToken);
 
    }
 
    @Override
    public User findByUserId(Integer userId) {
        return userRepository.findByUserId(userId);
    }
}