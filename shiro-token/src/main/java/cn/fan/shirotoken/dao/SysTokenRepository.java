package cn.fan.shirotoken.dao;

import cn.fan.shirotoken.entity.SysToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * TODO
 *
 * @author fgy
 * @version 1.0
 * @date 2020/12/18 15:14
 */
@Repository
public interface SysTokenRepository extends JpaRepository<SysToken, Integer> {
    SysToken findByToken(String accessToken);

    SysToken findByUserId(Integer userId);
}
