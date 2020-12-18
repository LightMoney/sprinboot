package cn.fan.shirotoken.dao;

import cn.fan.shirotoken.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * TODO
 *
 * @author fgy
 * @version 1.0
 * @date 2020/12/18 15:12
 */
@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
}
