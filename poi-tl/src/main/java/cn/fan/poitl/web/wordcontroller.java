package cn.fan.poitl.web;

import cn.fan.poitl.util.DocUtil;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.RenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @ Author     ：AZY.
 * @ Date       ：Created in 11:24 2019/8/19
 * @ Description：
 */
@RestController
public class wordcontroller {

    //执行导出
//    ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//    HttpServletRequest request = servletRequestAttributes.getRequest();
//    HttpServletResponse response = servletRequestAttributes.getResponse();

    @RequestMapping("getDoc")
    public void getDoc(){//HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("workname", "单位名称");
        String newWordName = "信息.docx";

        RowRenderData header = RowRenderData.build(new TextRenderData("1E90FF", "姓名"), new TextRenderData("1E90FF", "学历"));
        RowRenderData tt = new RowRenderData(Arrays.asList(new TextRenderData("姓名"), new TextRenderData("姓名")), "1E90FF");
        RowRenderData row0 = RowRenderData.build("张三", "研究生");
        RowRenderData row1 = RowRenderData.build("李四", "博士");
        RowRenderData row2 = RowRenderData.build("王五", "小学生");

        dataMap.put("table", new MiniTableRenderData(header, Arrays.asList(tt,row0, row1, row2)));

        //调用打印word的函数
        DocUtil.download(request, response, newWordName, dataMap);
    }

}
