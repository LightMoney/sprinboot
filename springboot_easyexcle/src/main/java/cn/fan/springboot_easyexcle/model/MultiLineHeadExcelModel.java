package cn.fan.springboot_easyexcle.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class MultiLineHeadExcelModel extends BaseRowModel {

    @ExcelProperty(value = {"年度", "年度", "年度"}, index = 0)
    private String p1;

    @ExcelProperty(value = {"一、订单", "一、订单", "日期"}, index = 1)
    private String p2;

    @ExcelProperty(value = {"一、订单", "一、订单", "订单号"}, index = 2)
    private String p3;

    @ExcelProperty(value = {"一、订单", "一、订单", "销售员"}, index = 3)
    private String p4;

    @ExcelProperty(value = {"一、订单", "一、订单", "组件号"}, index = 4)
    private String p5;

    @ExcelProperty(value = {"一、订单", "一、订单", "名称"}, index = 5)
    private String p6;

    @ExcelProperty(value = {"一、订单", "一、订单", "名称"}, index = 6)
    private String p7;

    @ExcelProperty(value = {"一、订单", "一、订单", "单位"}, index = 7)
    private String p8;

    @ExcelProperty(value = {"一、订单", "一、订单", "数量"}, index = 8)
    private String p9;
    @ExcelProperty(value = {"一、订单", "一、订单", "单价"}, index = 9)
    private String p10;


}