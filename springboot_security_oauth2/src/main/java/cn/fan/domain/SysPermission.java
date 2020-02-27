package cn.fan.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 权限实体类
 *
 * @author jitwxs
 * @since 2018/5/15 18:11
 */
@Data
public class SysPermission implements Serializable {
    static final long serialVersionUID = 1L;

    private Integer id;

    private String url;

    private Integer roleId;

    private String permission;

    private List permissions;

//url+role_id+permission 唯一标识了一个角色访问某一 url 时的权限，其中权限暂定为 c、r、u、d，即增删改查。
    public List getPermissions() {
        return Arrays.asList(this.permission.trim().split(","));
    }

    public void setPermissions(List permissions) {
        this.permissions = permissions;
    }
}
