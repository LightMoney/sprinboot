package cn.fan.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Auth Mr.luo
 * Date 2019/12/31 9:06
 **/
@Data
public class SysUser implements Serializable {

    private  Integer id;
    private  String name;
    private  String password;

}
