package cn.fan.springboot_easyexcle.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

/**
 * excle数据模型
 * 高版本可以不继承baserowmodel
 */
@Data
public class ExcelPropertyIndexModel extends BaseRowModel {
    @ExcelProperty(value = "姓名", index = 0)
    private String name;
    @ExcelProperty(value = "年龄", index = 1)
    private String age;
}
