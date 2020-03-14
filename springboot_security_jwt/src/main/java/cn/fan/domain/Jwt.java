package cn.fan.domain;

import cn.fan.util.JwtUtils;
import lombok.Data;

/**
 * jwt消息对象
 */
@Data
public class Jwt {
    //头部
    private String header;
    //负载
    private String payload;
    //签名
    private String signature;

    public Jwt(String payload) throws Exception {
        this.header= JwtUtils.encode(JwtUtils.DEFAULT_HEADER);
        this.payload = JwtUtils.encode(payload);
        this.signature= JwtUtils.getSignature(payload);
    }

    @Override
    public String toString() {
        return header+"."+payload+"."+signature;
    }
}
