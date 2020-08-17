package cn.fan.vaild.filter;

import cn.fan.vaild.token.SmsCodeAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 短信登录的鉴权过滤器，模仿 UsernamePasswordAuthenticationFilter 实现
 *
 * @author fgy
 * @since 2020/8/17
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    /**
     * form表单中手机号码的字段name
     */
    public static final String SPRING_SECURITY_FORM_MOBILE_KEY = "mobile";

    private String mobileParameter = SPRING_SECURITY_FORM_MOBILE_KEY;
    /**
     * 是否仅用post提交
     */
    private boolean postOnly = true;


    // 短信登录的请求 post 方式的 /login/sms
    public SmsCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login/sms", "POST"));
    }

    // ~ Methods
    // ========================================================================================================
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String mobile = obtainMobile(request);

        if (mobile == null) {
            mobile = "";
        }


        mobile = mobile.trim();

        SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * 获取手机号
     *
     * @param request
     * @return
     */
    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParameter);
    }


    /**
     * 设置信息到验证请求中
     *
     * @param request
     * @param authRequest
     */
    protected void setDetails(HttpServletRequest request,
                              SmsCodeAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }


    public void setMobileParameter(String mobileParameter) {
        Assert.hasText(mobileParameter, "mobile parameter must not be empty or null");
        this.mobileParameter = mobileParameter;
    }


    /**
     * 设置请求都是post
     * @param postOnly
     */
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getMobileParameter() {
        return mobileParameter;
    }

}
