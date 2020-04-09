package cn.fan.oauth2password.dao;


import cn.fan.oauth2password.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Auth Mr.luo
 * Date 2019/12/31 9:11
 **/
@Repository
@Mapper
public interface SysUserMapper {
    @Select("SELECT * FROM sys_user WHERE id = #{id}")
    SysUser selectById(Integer id);

    @Select("SELECT  id, name as username,password FROM sys_user WHERE name = #{username}")
    SysUser selectByName(String name);
}
