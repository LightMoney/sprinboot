package cn.fan.springboot_easyexcle.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class TestData {
    @ExcelProperty(value = "姓名", index = 2)
    private String name;
    @ExcelProperty(value = "密码", index = 1)
    private String password;
    @ExcelProperty(value = "其他", index = 3)
    private String dd;

}
