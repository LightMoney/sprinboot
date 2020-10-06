package com.unionpay.acp.sdk.constants;

import com.unionpay.acp.sdk.SDKConfig;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;



/**
 *
 * @author lcc
 * @data :2019年4月18日 上午10:24:57
 */
@Component
public class InitPay implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments var) throws Exception{
        SDKConfig.getConfig().loadPropertiesFromSrc();//银联
    }
}