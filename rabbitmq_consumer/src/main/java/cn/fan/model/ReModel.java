package cn.fan.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class ReModel  implements Serializable {
    private Long ll;
    private  String createTime;

    private String messageId;
    private Date testTime;
    private  String messageData;
    private BigDecimal bigData;



}
