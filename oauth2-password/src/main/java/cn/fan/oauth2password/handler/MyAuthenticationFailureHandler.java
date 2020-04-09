package cn.fan.oauth2password.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
      log.info("认证失败");
      response.setContentType("text/html;charset=utf-8");
      response.getWriter().write(exception.getMessage());

//        log.error("登录失败");
//        if (properties.getBrowser().getResultType().trim().equalsIgnoreCase(ResultType.JSON.toString())) {
//            // 失败了返回错误信息
//            httpServletResponse.setContentType("application/json;charset=utf-8");
//            String message = e.getMessage();
//            if (e instanceof BadCredentialsException) {
//                message = "密码错误！";
//            }
//            httpServletResponse.getWriter().println(objectMapper.writeValueAsString(new ResponseResult(false, message)));
//        } else {
//            // 调用默认实现
//            AuthenticationFailureHandler authenticationFailureHandler = new SimpleUrlAuthenticationFailureHandler();
//            authenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
//        }
    }
}
