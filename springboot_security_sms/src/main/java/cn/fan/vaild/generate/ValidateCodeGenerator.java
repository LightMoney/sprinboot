package cn.fan.vaild.generate;

import cn.fan.domain.vaild.ValidataCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码生成接口，具体实现类根据具体业务实现
 */
public interface ValidateCodeGenerator {
//    这里的传参设计成了ServletWebRequest是能够根据前端请求中的参数进行不同的业务实现
    ValidataCode generate(ServletWebRequest request);
}
