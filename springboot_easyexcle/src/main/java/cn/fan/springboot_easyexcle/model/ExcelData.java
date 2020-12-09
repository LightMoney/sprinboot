package cn.fan.springboot_easyexcle.model;

import lombok.Data;

import java.util.List;
import java.util.Map;



@Data
public class ExcelData<T> {
    /**
     * 文件名称
     **/
    private String fileName;
    /**
     * 表头
     **/
    private String[] heads;
    /**
     * 列
     **/
    private String[] cols;
    /**
     * 数据集合
     **/
    private List<T> list;


    /**
     * 合并单元格内容
     * key firstRow-lastRow-firstCol-lastCol  索引从0开始
     * value 合并之后替换的值
     * 支持单次针对一个sheet 的情况
     */
    private Map<String, String> mergeMap;

    public ExcelData(){

    }

    public ExcelData(String fileName, String[] heads, String[] cols, List<T> list) {
        this.fileName = fileName;
        this.heads = heads;
        this.cols = cols;
        this.list = list;
    }

    public ExcelData(String fileName, String[] heads, String[] cols, List<T> list, Map<String, String> mergeMap) {
        this.fileName = fileName;
        this.heads = heads;
        this.cols = cols;
        this.list = list;
        this.mergeMap = mergeMap;
    }
}
