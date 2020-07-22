package cn.fan.springboot_easyexcle;

import cn.fan.springboot_easyexcle.model.ExcelPropertyIndexModel;
import cn.fan.springboot_easyexcle.model.MultiLineHeadExcelModel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.event.adapter.DefaultAnalysisEventListenerAdapter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootEasyexcleApplicationTests {

    /**
     * 编辑表输出
     *
     * @throws IOException
     */
    @Test
    public void writeWithHead() throws IOException {
        try (OutputStream out = new FileOutputStream("withHead.xlsx");) {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            Sheet sheet1 = new Sheet(1, 0);
            sheet1.setSheetName("sheet1");
            List<List<String>> data = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                List<String> item = new ArrayList<>();
                item.add("item0" + i);
                item.add("item1" + i);
                item.add("item2" + i);
                data.add(item);
            }
            List<List<String>> head = new ArrayList<List<String>>();
            List<String> headCoulumn1 = new ArrayList<String>();
            List<String> headCoulumn2 = new ArrayList<String>();
            List<String> headCoulumn3 = new ArrayList<String>();
            headCoulumn1.add("第一列");
            headCoulumn2.add("第二列");
            headCoulumn3.add("第三列");
            head.add(headCoulumn1);
            head.add(headCoulumn2);
            head.add(headCoulumn3);
            Table table = new Table(1);
            table.setHead(head);
            writer.write0(data, sheet1, table);
            writer.finish();
        }
    }

    /**
     * 通过模型来输出文档 单表头
     */
    @Test
    public void writeByModelExcle()  throws IOException {
        try (OutputStream out = new FileOutputStream("withHead.xlsx");) {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            Sheet sheet1 = new Sheet(1, 0, ExcelPropertyIndexModel.class);
            sheet1.setSheetName("sheet1");
            List<ExcelPropertyIndexModel> data = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                ExcelPropertyIndexModel item = new ExcelPropertyIndexModel();
                item.setName( "name" + i);
                item.setAge("age"+i);
                data.add(item);
            }
            writer.write(data, sheet1);
            writer.finish();
        }

    }

    /**
     * 多表头复杂操作  写
     * @throws IOException
     */
    @Test
    public void writeWithMultiHead() throws IOException {
        try (OutputStream out = new FileOutputStream("withMultiHead.xlsx");) {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            Sheet sheet1 = new Sheet(1, 0, MultiLineHeadExcelModel.class);
            sheet1.setSheetName("sheet1");
            List<MultiLineHeadExcelModel> data = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                MultiLineHeadExcelModel item = new MultiLineHeadExcelModel();
                item.setP1("p1" + i);
                item.setP2("p2" + i);
                item.setP3("p3" + i);
                item.setP4("p4" + i);
                item.setP5("p5" + i);
                item.setP6("p6" + i);
                item.setP7("p7" + i);
                item.setP8("p8" + i);
                item.setP9("p9" + i);
                data.add(item);
            }
            writer.write(data, sheet1);
            writer.finish();
        }
    }

    @Test
    public void read() throws Exception {
        try (InputStream in = new FileInputStream("withHead.xlsx");) {
//            AnalysisEventListener<List<String>> listener = new AnalysisEventListener<List<String>>() {
//
//                @Override
//                public void invoke(List<String> object, AnalysisContext context) {
//                    System.err.println("Row:" + context.getCurrentRowNum() + " Data:" + object);
//                }
//
//                @Override
//                public void doAfterAllAnalysed(AnalysisContext context) {
//                    System.err.println("doAfterAllAnalysed...");
//                }
//            };
            AnalysisEventListener<List<String>> listener=new DefaultAnalysisEventListenerAdapter<>();
            ExcelReader excelReader =new ExcelReader(in,null,listener);
            excelReader.read();
            System.err.println("data:"+listener.getData());
        }
    }

    /**
     * 读表通过模型
     * @throws Exception
     */
    @Test
    public void readModel() throws Exception {
        try (InputStream in = new FileInputStream("withHead.xlsx");) {
//            AnalysisEventListener<ExcelPropertyIndexModel> listener = new AnalysisEventListener<ExcelPropertyIndexModel>() {
//
//                @Override
//                public void invoke(ExcelPropertyIndexModel object, AnalysisContext context) {
//                    System.err.println("Row:" + context.getCurrentRowNum() + " Data:" + object);
//                }
//
//                @Override
//                public void doAfterAllAnalysed(AnalysisContext context) {
//                    System.err.println("doAfterAllAnalysed...");
//                }
//            };
            AnalysisEventListener<ExcelPropertyIndexModel> listener=new DefaultAnalysisEventListenerAdapter<>();
            ExcelReader excelReader = new ExcelReader(in, null, listener);
            // 第二个参数为表头行数，按照实际设置
            excelReader.read(new Sheet(1, 1, ExcelPropertyIndexModel.class));
        }

    }
//下载
//    @Override
//    public void fileDownload(@RequestBody Map map) {
//        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
//        OutputStream out = null;
//        ExcelWriter writer = null;
//
//        List<ReportExcleModel> data = fixService.fileDownload(map);
//        //拼接导出的文件名
//        String fileName = "FaultReport";
//        try {
//            response.addHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
//            //下载到浏览器默认地址
//            out = response.getOutputStream();
//            writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
//            Sheet sheet1 = new Sheet(1, 0, ReportExcleModel.class);
//            sheet1.setSheetName("sheet1");
//            writer.write(data, sheet1);
//        } catch (Exception e) {
//            log.error("下载失败" + e.getMessage(), e);
//        } finally {
//            try {
//                out.flush();
//                writer.finish();
//                out.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//}
}
