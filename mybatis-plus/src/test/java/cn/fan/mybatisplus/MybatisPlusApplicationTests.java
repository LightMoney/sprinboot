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

import java.time.LocalDateTime;
import java.util.ArrayList;
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
//乐观锁测试
//
//    乐观锁实现方式：
//
//    取出记录时，获取当前version
//    更新时，带上这个version
//    执行更新时， set version = newVersion where version = oldVersion
//    如果version不对，就更新失败
//    特别说明:
//    支持的数据类型只有:int,Integer,long,Long,Date,Timestamp,LocalDateTime
//    整数类型下 newVersion = oldVersion + 1
//    newVersion 会回写到 entity 中
//    仅支持 updateById(id) 与 update(entity, wrapper) 方法
//    在 update(entity, wrapper) 方法下, wrapper 不能复用!!!

    @Test
    public void test06() {
        User user=new User();
        User byId = userService.getById(1005);
//        byId.setVersion(1);
//        user.setUid(1006);
//        user.setUname("和平");
        user.setAge(20);
//        int result= userMapper.insert(user);
//        userService.save(user);
//        userService.update()
//        userService.updateById(byId);
        userService.saveOrUpdate(byId);
//        System.out.println(result);
    }

    @Test
    public void test07() {
        User user=new User();

//        byId.setVersion(1);
//        user.setUid(1006);
        user.setUname("和平");
        user.setAge(20);
//        int result= userMapper.insert(user);
//        userService.save(user);
//        userService.update()
//        userService.updateById(byId);
        userService.saveOrUpdate(user);
//        System.out.println(result);
    }

    /**
     * 如果数据在and里就会用括号包起来，若单独or（），直接在or或and后面直接拼接
     */
    @Test
    public void test8(){
        QueryWrapper<User> wrapper=new QueryWrapper();
        wrapper.lambda().like(User::getUname,"l").and(i->i.eq(User::getAge,12).or().eq(User::getAge,13));
        List<User> list = userService.list(wrapper);
        log.info("====="+list);
    }


//   3.0.7和以上版本已支持逻辑删除的扩展，可通过配置和注解实现

    @Test
    public void test9(){

        List<User> list=new ArrayList<>();
        User use1=new User();
        use1.setUname("kissse");
        use1.setAge(34);
        User use2=new User();
        use2.setUname("jissse");
        use2.setAge(30);
        User use3=new User();
//        use3.setUid(3);
        use3.setUname("duplicse");
        use3.setAge(31);
       list.add(use1);
       list.add(use2);
       list.add(use3);
        //如果只有新增，返回值与输入值size一致，有更新则大于
        userMapper.testDuplicate(list);
    }
}
