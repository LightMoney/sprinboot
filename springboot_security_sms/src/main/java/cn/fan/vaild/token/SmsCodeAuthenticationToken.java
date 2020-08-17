package cn.fan.vaild.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * 短信登录 AuthenticationToken，模仿 UsernamePasswordAuthenticationToken 实现
 * @author fgy
 * @since 2020/8/17
 */
public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    /**
     * 在 UsernamePasswordAuthenticationToken 中该字段代表登录的用户名，
     * 在这里就代表登录的手机号码
     */
    private final Object principal;
//    private Object credentials;



    /**
     * 构建一个没有鉴权的 SmsCodeAuthenticationToken
     */
    public SmsCodeAuthenticationToken(Object principal) {
        super(null);
        this.principal = principal;
//        this.credentials = credentials;
        setAuthenticated(false);
    }

    /**
     * 构建拥有鉴权的 SmsCodeAuthenticationToken
     */
    public SmsCodeAuthenticationToken(Object principal,
                                      Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
//        this.credentials = credentials;
        super.setAuthenticated(true); // must use super, as we override
    }

    // ~ Methods
    // ========================================================================================================
    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
//        credentials = null;
    }
}

