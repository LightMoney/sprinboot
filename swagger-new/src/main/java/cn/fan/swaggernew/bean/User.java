package cn.fan.swaggernew.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>Title: User.java</p>
 * <p>Description: 用户类</p>
 * <p>Copyright: Copyright (c) 2019</p>
 *
 * @author Xiao Nong
 * @version 1.0
 * @date 2019年7月16日
 *
 * @ApiModel：用在返回对象类上，描述一个Model的信息（这种一般用在post创建的时候
 * ，使用@RequestBody这样的场景，请求参数无法使用@ApiImplicitParam注解进行描述的时候）
 *   @ApiModelProperty：用在出入参数对象的字段上，表示对model属性的说明或者数据操作更改
 *
 */
@ApiModel(value = "user对象", description = "新增&更新用户对象说明")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(hidden = true,example = "4564")
    private Integer id;

    @ApiModelProperty(name = "userName", value = "用户名称", required = true, example = "张三")
    private String userName;

    @ApiModelProperty(name = "passWord", value = "密码", required = true, example = "123456")
    private String passWord;

    @ApiModelProperty(name = "email", value = "邮箱", required = true, example = "123456@163.com")
    private String email;

    @ApiModelProperty(name = "nickName", value = "昵称", required = true, example = "瑞雪狸猫")
    private String nickName;

    @ApiModelProperty(hidden = true,example = "weqe")
    private String regTime;

    @ApiModelProperty(name = "age", value = "年龄", required = true, example = "18")
    private Integer age;


    public User() {
        super();
    }

    public User(String email, String nickName, String passWord, String userName, String regTime) {
        super();
        this.email = email;
        this.nickName = nickName;
        this.passWord = passWord;
        this.userName = userName;
        this.regTime = regTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
