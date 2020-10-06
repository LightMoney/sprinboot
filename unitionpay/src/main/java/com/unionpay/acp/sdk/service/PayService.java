package com.unionpay.acp.sdk.service;

import com.unionpay.acp.sdk.model.Product;

import java.util.Map;



/**
 * 支付测试
 * @author lcc
 * @data :2018年11月28日 下午2:54:23
 */

public interface PayService {
    /**
     * 银联支付
     * @Author  科帮网
     * @param product
     * @return  String
     */
    String unionPay(Product product);
    /**
     * 前台回调验证
     * @Author  科帮网
     * @param valideData
     * @param encoding
     * @return  String
     *
     */
    String validate(Map<String, String> valideData, String encoding);
    /**
     * 对账单下载
     */
    void fileTransfer();
}