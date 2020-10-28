package cn.fan.swaggernew.dao;


import cn.fan.swaggernew.bean.User;

/**
 * <p>Title: UserMapper.java</p>
 * <p>Description:DAO 数据处理类 </p>
 * <p>Copyright: Copyright (c) 2019</p>
 *
 * @author Xiao Nong
 * @version 1.0
 * @date 2019年7月16日
 */

public interface UserMapper {

    int deleteById(Long id);

    int createUser(User user);

    User selectById(Long id);

    int modifyUser(User user);

}
