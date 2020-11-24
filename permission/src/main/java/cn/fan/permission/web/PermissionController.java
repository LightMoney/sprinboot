package cn.fan.permission.web;

import cn.fan.permission.entity.vo.ModuleVo;
import cn.fan.permission.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api(value = "权限接口", tags = "PermissionController", description = "测试权限接口相关")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @ApiOperation("通过当前用户获取用户所拥有权限的模块（菜单）（此时返回的模块权限会剔除掉无任何权限的模块")
    @GetMapping("/get/module/admin")
    public List<ModuleVo> getModulePermissionByMe() {
        //获取该用户的模块（通过用户id这里写死，实际为动态获取）
        List<ModuleVo> vos = permissionService.queryModPerByTypeAndAdminId(null, 119);
        //排除没有权限的模块
        List<ModuleVo> res = permissionService.excludeModule(vos);
        if (res == null) {
            res = new ArrayList<>();
        }
        addHomeModule(res);
        return res;
    }

    private void addHomeModule(List<ModuleVo> vos) {
        ModuleVo vo = new ModuleVo();
        vo.setModuleName("首页");
        vo.setKeyword("home");
        vo.setFlag("fold");
        vo.setModuleId(1);
        vos.add(0, vo);
    }
}
