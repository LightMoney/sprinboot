package cn.fan.shirotoken.dao;

import cn.fan.shirotoken.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * TODO
 *
 * @author fgy
 * @version 1.0
 * @date 2020/12/18 15:10
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission,Integer> {
}
