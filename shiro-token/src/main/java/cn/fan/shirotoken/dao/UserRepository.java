package cn.fan.shirotoken.dao;

import cn.fan.shirotoken.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * TODO
 *
 * @author fgy
 * @version 1.0
 * @date 2020/12/18 15:13
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
     User findByUsername(String username);
     User findByUserId(Integer userId);
}
