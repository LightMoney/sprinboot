package cn.fan.swaggernew.service;


import cn.fan.swaggernew.bean.User;

public interface UserService {
    int deleteById(Long id);

    int createUser(User user);

    User selectById(Long id);

    int modifyUser(User user);

}
