package cn.fan.domain.vaild;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 验证码公共类
 * 验证码  过期时间
 */
@Data
public class ValidataCode {

    private String code;

    private LocalDateTime expireTime;

    public ValidataCode(String code, LocalDateTime exprieTime) {
        this.code = code;
        this.expireTime = exprieTime;

    }

    public ValidataCode(String code, int inExprie) {
        this.code = code;
//        对当前时间添加过期时间然后返回
        this.expireTime = LocalDateTime.now().plusSeconds(inExprie);

    }

    /**
     * 判断是否过期
     *
     * @return
     */
    public boolean isExprie() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
