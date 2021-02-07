package cn.fan.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
@TableName("tb_user")
public class User {
    @TableId(type = IdType.AUTO)// 需要指定，否则无法新增后拿到回调的id，以及进行删除等操作
    private Integer uid;

    private String uname;

    private Integer age;

    private Integer rid;
    //测试使用java8的时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //输出参数的格式
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")  //接收参数的格式
    private LocalDateTime ttime;
    //乐观锁版本控制 数据库设置该字段默认值为1
    @Version
    private Integer version;
//    public User(String uname, Integer age) {
//        this.uname = uname;
//        this.age = age;
//    }
}
