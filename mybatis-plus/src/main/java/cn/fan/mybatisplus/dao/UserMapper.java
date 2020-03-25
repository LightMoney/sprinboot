package cn.fan.mybatisplus.dao;

import cn.fan.mybatisplus.entity.User;
import cn.fan.mybatisplus.entity.UserVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {
    List<UserVo>  selectByPage(Page<UserVo> page);
}
