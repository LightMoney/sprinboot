package cn.fan.springboot_easyexcle.model;

import lombok.Data;

import java.util.Date;

@Data
public class FillData {
    private Date  date;
    private String ticketNo;
    private  String type;
    private String customerName;
    private String productName;
    private String productType;
    private  String unit;
    private Integer num;
    private Double one;
    private  Double countOne;
}