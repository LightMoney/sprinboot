package cn.fan.dao;

import cn.fan.domain.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SysRoleMapper {
    @Select("SELECT * FROM sys_role WHERE id = #{id}")
    SysRole selectById(Integer id);

    @Select("SELECT * FROM sys_role WHERE name = #{name}")
    SysRole selectByName(String name);
}