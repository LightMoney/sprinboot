package cn.fan.mybatisplus.service.impl;

import cn.fan.mybatisplus.dao.UserMapper;
import cn.fan.mybatisplus.entity.User;
import cn.fan.mybatisplus.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
