package cn.fan.controller;

import cn.fan.dao.SysUserMapper;
import cn.fan.domain.SysUser;
import cn.fan.result.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class LoginController {
    //private Logger logger = LoggerFactory.getLogger(LoginController.class);\

//    为了测试就直接写dao层，实际中应为业务层
    @Autowired
    private SysUserMapper sysUserMapper;

    @RequestMapping("/")
    public String showHome() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("当前登陆用户：" + name);
        return "home";
    }


    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getAdminInfo(Principal principal) {
        if (principal == null) {
            return ResponseResult.FAIL();
        }
        String username = principal.getName();
        SysUser sysUser=sysUserMapper.selectByName(username);
        Map<String, Object> data = new HashMap<>();
        data.put("username", sysUser.getUsername());
        data.put("roles", new String[]{"TEST"});
//        data.put("menus", roleService.getMenuList(umsAdmin.getId()));
//        data.put("icon", umsAdmin.getIcon());
        return new ResponseResult(true,data);
    }
    @RequestMapping(value = "/message", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getMessage(){
      User o=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseResult(true,o);
    }
//    @RequestMapping("/login")
//    public String showLogin() {
//        return "login";
//    }

    @RequestMapping("/admin")
    @ResponseBody
    @PreAuthorize("hasPermission('/admin','r')")
    public String printAdminR() {
        return "如果你看见这句话，说明你访问/admin路径具有r权限";
    }

    @RequestMapping("/admin/c")
    @ResponseBody
//    @PreAuthorize("hasPermission('/admin','c')")
    public String printAdminC() {
        return "如果你看见这句话，说明你访问/admin路径具有c权限";
    }

    @RequestMapping("/testtr/c")
    @ResponseBody
    public String printTestA() {
        return "如果你看见这句话，说明你访问/testtr/c路径具有c权限";
    }

    //为了演示，我只是简单的将错误信息返回给了页面
    @RequestMapping("/login/error")
    public void loginError(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        AuthenticationException exception =
                (AuthenticationException) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        try {
            response.getWriter().write(exception.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
