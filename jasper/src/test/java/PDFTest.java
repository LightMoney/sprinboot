import cn.fan.util.TestFileUtil;
import jdk.internal.dynalink.beans.StaticClass;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.view.JasperViewer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileInputStream;
import java.util.HashMap;

public class PDFTest {

    public static void main(String[] args) {
        createJasper();
//        createJrprint();
//        showPdf();
    }

//    读取设计的模板生成jasper 文件
    public static void createJasper(){
        try{
            String path = TestFileUtil.getPath()+ "static\\test01.jrxml";
//            好像输出找不到
//           Resource classPathResource = new ClassPathResource("template/test01.jrxml");
//            JasperCompileManager.compileReport(new FileInputStream(classPathResource.getFile()));
            JasperCompileManager.compileReportToFile(path);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
//创建输出
    public static void createJrprint(){
        try{
            String path = TestFileUtil.getPath()+ "static\\test01.jasper";
            //通过空参数和空数据源进行填充
            JasperFillManager.fillReportToFile(path,new HashMap(),new JREmptyDataSource());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
//生成预览
    public static void showPdf(){
        try{
            String path = TestFileUtil.getPath()+ "static\\test01.jrprint";
            JasperViewer.viewReport(path,false);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
