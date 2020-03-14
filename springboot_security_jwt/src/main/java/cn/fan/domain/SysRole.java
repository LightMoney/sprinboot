package cn.fan.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@Data
public class SysRole implements GrantedAuthority, Serializable {
    static final long serialVersionUID = 1L;

    private Integer id;

    private String authority;


}
