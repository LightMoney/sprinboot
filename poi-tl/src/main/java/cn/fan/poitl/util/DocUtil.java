package cn.fan.poitl.util;

import com.deepoove.poi.XWPFTemplate;
import org.springframework.util.ClassUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Map;


public class DocUtil {

    public static void download(HttpServletRequest request, HttpServletResponse response, String newWordName, Map dataMap)  {

        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        XWPFTemplate template = XWPFTemplate.compile(path+"static/templates/word.docx").render(dataMap);
        OutputStream out = null;
        try {
            out = new FileOutputStream("out_template.docx");
            template.write(out);
            out.flush();
            out.close();
            template.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        InputStream fis = null;
        OutputStream toClient = null;
        File file = new File("out_template.docx");
        try {
            fis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            newWordName = URLEncoder.encode(newWordName, "utf-8"); //这里要用URLEncoder转下才能正确显示中文名称
            response.addHeader("Content-Disposition", "attachment;filename=" + newWordName+"");
            response.addHeader("Content-Length", "" + file.length());
            toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                if(fis!=null){
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(toClient!=null){
                    toClient.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


