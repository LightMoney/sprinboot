package cn.fan.shirotoken.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@Entity
public class SysToken {

    @Id
    private Integer userId;
    private String token;
    private Date expireTime;
    private Date updateTime;
}