package cn.fan.testfunction.utils;

import java.text.DecimalFormat;
import java.text.Format;

public class VersionUtil {


    public String createVerisionNumber(String oldVersion,Boolean flag) {
        //拆分
        String[] strarray=oldVersion.split("_");
        //先判断是不是进行大版本的跟新
        //转换格式
        Format f1 = new DecimalFormat("000");
        Format f2 = new DecimalFormat("00");
        if(flag){
            //大版本
          return  strarray[0]+"_"+(f2.format(Integer.parseInt(strarray[1]) +1))+"_"+"001";
        }else {
            return strarray[0]+"_"+(f2.format(Integer.parseInt(strarray[1])) +"_"+(f1.format(Integer.parseInt(strarray[1]) +1)));
        }
    }

    public static void main(String[] args) {
        VersionUtil info=new VersionUtil();
    }
}
