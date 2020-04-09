package cn.fan.mybatisplus;

import cn.fan.mybatisplus.dao.UserMapper;
import cn.fan.mybatisplus.entity.User;
import cn.fan.mybatisplus.entity.UserVo;
import cn.fan.mybatisplus.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MybatisPlusApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;
    @Test
    public void test01() {
log.info("hjdsfh");
        Page<User> page = new Page<User>(1, 1);
        // 1.进行查询
//        List<User> users = userMapper.selectList(null);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getUname, "小红");
        IPage<User> page1 = userMapper.selectPage(page, wrapper);
        System.out.println(page1.toString());
//        int result = userMapper.selectCount(null);
        // 2.遍历打印输出
//        for (User user : users) {
//            System.out.println(user);
////            System.out.println(result);
//        }
    }

    //自定义方法分页
    @Test
    public void test02() {
        Page<UserVo> page = new Page<UserVo>(1, 1);
        List<UserVo> userVos = userMapper.selectByPage(page);
        page.setRecords(userVos);
        System.out.println(userVos.toString());
    }

    @Test
    public void test03() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getUname, "小红");
        userMapper.delete(wrapper);

    }

    //    pagehepler
    @Test
    public void test04() {
        PageHelper.startPage(1, 1);
        List<User> users = userMapper.selectList(null);
        PageInfo<User> pageInfo = new PageInfo<User>(users);
        System.out.println(pageInfo);
    }
//测试更新
    @Test
    public void test05() {
//
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getUname, "小红");
        User user=new User();
//        user.setUid(1002);
        user.setUname("大白");

//      int result= userMapper.updateById(user);
     userService.update(user,wrapper);

//        System.out.println(result);
    }

    @Test
    public void test06() {
        User user=new User();
//        user.setUid(1006);
//        user.setUname(null);
//        int result= userMapper.insert(user);
        userService.save(user);
//        System.out.println(result);
    }
}
