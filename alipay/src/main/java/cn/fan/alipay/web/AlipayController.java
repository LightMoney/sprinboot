package cn.fan.alipay.web;

import cn.fan.alipay.service.AlipayService;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付宝
 * @author lcc
 * @data :2018年6月4日 上午10:55:46
 */
@RestController
public class AlipayController {

    @Autowired
    @Qualifier("alipayService")
    private AlipayService alipayService;
    
    /**
     * web 订单支付
     */
    @GetMapping("getPagePay")
    public String getPagePay() throws Exception{
        /** 模仿数据库，从后台调数据*/
        String outTradeNo = "9987821";
        Integer totalAmount = 411;
        String subject = "苹果1";
        
        String pay = alipayService.webPagePay(outTradeNo, totalAmount, subject);
        System.out.println(pay);
        
//        Map<Object, Object> pays = new HashMap<>();
//        pays.put("pay", pay);
        
        return pay.toString();
    }
    
    /**
     * app 订单支付
     */
    @GetMapping("getAppPagePay")
    public String getAppPagePay() throws Exception{
        /** 模仿数据库，从后台调数据*/
        String outTradeNo = "131233";
        Integer totalAmount = 1000;
        String subject = "天猫超市012";
        
        String pay = alipayService.appPagePay(outTradeNo, totalAmount, subject);
        
        Object json = JSONObject.toJSON(pay);
        
        System.out.println(json);
        
        return json.toString();
    }
    
    /**
     * 交易查询
     */
    @PostMapping("aipayQuery")
    public String alipayQuery(@RequestBody Map map) throws Exception{
        /**调取支付订单号*/
//        String outTradeNo = "99603621";
       String outTradeNo= map.get("outTradeNo").toString();
        String query = alipayService.query(outTradeNo);
        
        Object json = JSONObject.toJSON(query);
        
        /*JSONObject jObject = new JSONObject();
        jObject.get(query);*/
        return json.toString();
    }
    
    /**
     * 退款
     * @throws AlipayApiException 
     */
    @GetMapping("alipayRefund")
    public String alipayRefund(
            @RequestParam("outTradeNo")String outTradeNo,
            @RequestParam(value = "outRequestNo", required = false)String outRequestNo,
            @RequestParam(value = "refundAmount", required = false)Integer refundAmount
            ) throws AlipayApiException {
        
        /** 调取数据*/
        //String outTradeNo = "15382028806591197"; 订单号是必填的
        String refundReason = "用户不想购买";
        //refundAmount = 1;
        //outRequestNo = "22";
        
        String refund = alipayService.refund(outTradeNo, refundReason, refundAmount, outRequestNo);
        
        System.out.println(refund);
        
        return refund.toString();
    }
    
    /**
     * 退款查询
     * @throws AlipayApiException 
     */
    @PostMapping("refundQuery")
    public String refundQuery() throws AlipayApiException{
        
        /** 调取数据*/
        String outTradeNo = "13123"; 
        String outRequestNo = "2"; 
        
        String refund = alipayService.refundQuery(outTradeNo, outRequestNo);
        
        return refund.toString();
        
    }
    
    /**
     * 交易关闭
     * @throws AlipayApiException 
     */
    @PostMapping("alipayclose")
    public String alipaycolse() throws AlipayApiException{
        
        /** 调取数据*/
        String outTradeNo = "13123";
        
        String close = alipayService.close(outTradeNo);
        
        return close.toString();
    }
    
}

