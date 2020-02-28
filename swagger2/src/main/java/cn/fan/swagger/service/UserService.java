package cn.fan.swagger.service;


import cn.fan.swagger.bean.User;

public interface UserService {
    int deleteById(Long id);

    int createUser(User user);

    User selectById(Long id);

    int modifyUser(User user);

}
