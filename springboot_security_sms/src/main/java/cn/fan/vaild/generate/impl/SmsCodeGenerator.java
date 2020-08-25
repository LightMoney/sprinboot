package cn.fan.vaild.generate.impl;

import cn.fan.domain.vaild.SecurityConstants;
import cn.fan.domain.vaild.ValidataCode;
import cn.fan.vaild.generate.ValidateCodeGenerator;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

@Component("smsCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {
    @Override
    public ValidataCode generate(ServletWebRequest request) {
//        String smsCode = request.getParameter("smsCode");
        String code = RandomStringUtils.randomNumeric(6);
        return new ValidataCode(code, SecurityConstants.SMS_EXPIRE_SECOND);
    }
}
