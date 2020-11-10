package cn.fan.swagger2.service.impl;


import cn.fan.swagger2.bean.User;
import cn.fan.swagger2.dao.UserMapper;
import cn.fan.swagger2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int deleteById(Long id) {
        return userMapper.deleteById(id);
    }

    @Override
    public int createUser(User user) {
        return userMapper.createUser(user);
    }

    @Override
    public User selectById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public int modifyUser(User user) {
        return userMapper.modifyUser(user);
    }
}
