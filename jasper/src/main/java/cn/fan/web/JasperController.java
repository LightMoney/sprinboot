package cn.fan.web;

import net.sf.jasperreports.engine.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;


@RestController
public class JasperController {

    @GetMapping("/test/jasper")
    public void creatPdf(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServletOutputStream os = response.getOutputStream();
        //1.引入使用工具建好的jasper 文件
        Resource classPathResource = new ClassPathResource("template/second.jasper");
        FileInputStream inputStream = new FileInputStream(classPathResource.getFile());
//        2.创建jasperPrint，向jasper 中填充数据
        try {
//            inputStream:  jasper文件输入流
//            new  hashmap:   向模板中输入的参数
//            jasperDatasource：  数据源 （和数据库数据源不同）   该参数不传不会异常，会输出空白文档
//                      填充模板的数据来源（connection， javabean    map）
//                      填充空数据来源   JREmptyDataSource
            JasperPrint print = JasperFillManager.fillReport(inputStream, new HashMap<>(), new JREmptyDataSource());
//            3.将 jasperPrint 输出 为pdf
            JasperExportManager.exportReportToPdfStream(print, os);
        } catch (JRException e) {
            e.printStackTrace();
        } finally {
            os.flush();
            os.close();
        }
    }
}
