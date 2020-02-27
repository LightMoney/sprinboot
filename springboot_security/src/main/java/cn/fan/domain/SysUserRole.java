package cn.fan.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysUserRole implements Serializable {
    static final long serialVersionUID = 1L;

    private Integer userId;

    private Integer roleId;


}
