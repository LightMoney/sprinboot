package cn.fan.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@Slf4j
public class LoginController {
    //private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/")
    public String showHome() {
        //获取当前用户相当于principle
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("当前登陆用户：" + name);
        return "home";
    }

    @RequestMapping("/login")
    public String showLogin() {
        return "login";
    }

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
    public String printTestA(){
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
