package cn.fan.springboot_easyexcle.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserData {
    @ExcelProperty(value = "用户名", index = 1)
    private String name;
    @ExcelProperty(value = "手机号", index = 2)
    private String mobile;
    @ExcelProperty(value = "工号", index = 3)
    private String workno;
    @ExcelProperty(value = "聘用形式", index = 4)
    private Integer em;
    @ExcelProperty(value = "入职时间", index = 5)
    private Date reg;
    @ExcelProperty(value = "部门编码", index = 6)
    private String deptno;

}
