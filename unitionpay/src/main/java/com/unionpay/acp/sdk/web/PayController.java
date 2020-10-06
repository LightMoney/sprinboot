package com.unionpay.acp.sdk.web;

import com.unionpay.acp.sdk.model.Product;
import com.unionpay.acp.sdk.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * 支付测试
 * @author lcc
 * @data :2018年11月28日 下午2:50:34
 */
@RestController
public class PayController {
    
    @Autowired
    private PayService payService;
    
    /**
     * 电脑支付
     * @param product
     * @return
     */
    @RequestMapping(value="pcPay")
    public String  pcPay(Product product) {
        product.setPayWay((short)2);
        String form  =  payService.unionPay(product);
        return form;
    }
}