package cn.fan.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
@TableName("tb_user")
public class User {
    @TableId(type = IdType.AUTO)// 需要指定，否则无法新增后拿到回调的id，以及进行删除等操作
    private  Integer uid;

    private  String uname;

    private  Integer age;

    private Integer rid;

//    public User(String uname, Integer age) {
//        this.uname = uname;
//        this.age = age;
//    }
}
