package cn.fan.swagger2.service;


import cn.fan.swagger2.bean.User;

public interface UserService {
    int deleteById(Long id);

    int createUser(User user);

    User selectById(Long id);

    int modifyUser(User user);

}
