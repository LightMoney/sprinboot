package cn.fan.springboot_easyexcle;

import cn.fan.springboot_easyexcle.listener.DemoDataListener;
import cn.fan.springboot_easyexcle.model.ExcelPropertyIndexModel;
import cn.fan.springboot_easyexcle.model.FillData;
import cn.fan.springboot_easyexcle.model.MultiLineHeadExcelModel;
//import cn.fan.springboot_easyexcle.util.EasyExcelUtil;
import cn.fan.springboot_easyexcle.model.TestData;
import cn.fan.springboot_easyexcle.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
//import com.alibaba.excel.event.adapter.DefaultAnalysisEventListenerAdapter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * easypoi 测试
 */
//@RunWith(SpringRunner.class)
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
    public void writeByModelExcle() throws IOException {
        try (OutputStream out = new FileOutputStream("withHead.xlsx");) {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            Sheet sheet1 = new Sheet(1, 0, ExcelPropertyIndexModel.class);
            sheet1.setSheetName("sheet1");
            List<ExcelPropertyIndexModel> data = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                ExcelPropertyIndexModel item = new ExcelPropertyIndexModel();
                item.setName("name" + i);
                item.setAge("age" + i);
                data.add(item);
            }
            writer.write(data, sheet1);
            writer.finish();
        }

    }

    /**
     * 多表头复杂操作  写
     *
     * @throws IOException
     */
    @Test
    public void writeWithMultiHead() throws IOException {
        try (OutputStream out = new FileOutputStream("withMultiHead.xlsx");) {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            for (int j = 1; j < 5; j++) {
                Sheet sheet1 = new Sheet(j, 0, MultiLineHeadExcelModel.class);
                sheet1.setSheetName("sheet" + j);
                Table table1 = new Table(1);

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
                    item.setP10("p10" + i);
                    data.add(item);
                }
                writer.write(data, sheet1);
            }
//
//            Sheet sheet2 = new Sheet(2, 0, MultiLineHeadExcelModel.class);
//            sheet2.setSheetName("sheet2");
//            List<MultiLineHeadExcelModel> data2 = new ArrayList<>();
//            for (int i = 0; i < 100; i++) {
//                MultiLineHeadExcelModel item2 = new MultiLineHeadExcelModel();
//                item2.setP1("p1" + i);
//                item2.setP2("p2" + i);
//                item2.setP3("p3" + i);
//                item2.setP4("p4" + i);
//                item2.setP5("p5" + i);
//                item2.setP6("p6" + i);
//                item2.setP7("p7" + i);
//                item2.setP8("p8" + i);
//                item2.setP9("p9" + i);
//                item2.setP10("p10" + i);
//                data2.add(item2);
//            }
//
//            writer.write(data2, sheet2);
            writer.finish();
        }
    }

    @Test
    public void read() throws Exception {
        StopWatch watch = new StopWatch();
        watch.start();
        try (InputStream in = new FileInputStream("D:\\BaiduNetdiskDownload\\26-传统行业SaaS解决方案\\08-员工管理及POI\\02-POI报表的高级应用\\资源\\百万数据报表\\demo.xlsx");) {
            DemoDataListener demoDataListener = new DemoDataListener();
//            List<Object> objects = EasyExcel.read(in,demoDataListener).sheet(0).doReadSync();
//            读取时 可以对模型数据  字段指定@ExcelProperty(value = "其他", index = 3) 来对应表格中的某一行
            List<TestData> objects1 = EasyExcel.read(in, TestData.class, demoDataListener).sheet().doReadSync();
            watch.stop();
            //104万  7列数据 18.83s
          System.out.println(watch.getTotalTimeSeconds());
//            System.err.println("data:" + demoDataListener.getHeadList());
//            System.out.println("listen:" + demoDataListener.getList());

        }
    }

    /**
     * 读表通过模型
     *
     * @throws Exception
     */
//    @Test
//    public void readModel() throws Exception {
//        try (InputStream in = new FileInputStream("withHead.xlsx");) {
////            AnalysisEventListener<ExcelPropertyIndexModel> listener = new AnalysisEventListener<ExcelPropertyIndexModel>() {
////
////                @Override
////                public void invoke(ExcelPropertyIndexModel object, AnalysisContext context) {
////                    System.err.println("Row:" + context.getCurrentRowNum() + " Data:" + object);
////                }
////
////                @Override
////                public void doAfterAllAnalysed(AnalysisContext context) {
////                    System.err.println("doAfterAllAnalysed...");
////                }
////            };
//            AnalysisEventListener<ExcelPropertyIndexModel> listener = new DefaultAnalysisEventListenerAdapter<>();
//            ExcelReader excelReader = new ExcelReader(in, null, listener);
//            // 第二个参数为表头行数，按照实际设置
//            excelReader.read(new Sheet(1, 1, ExcelPropertyIndexModel.class));
//        }
//
//    }

    /**
     * 使用对应模板导出文档
     * 注意sheetNO号要写上，否则无法写入
     * 需要测试下打包后在服务器上路径是否正确
     * 该方法适用于单sheet模板数据导入
     *
     * @throws IOException
     */
    @Test
    public void templateOut() throws IOException {
//        try( OutputStream out = new FileOutputStream("tset.xlsx");) {
//            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("static/template2.xlsx");
//        String templateFileName = this.getClass().getResource("/static/").getPath() + "template2.xlsx";

//运行时读取的是target下的路径，可大断点查看
        String templateFileName = TestFileUtil.getPath() + "static" + File.separator + "template2.xlsx";
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // {} 代表普通变量 {.} 代表是list的变量
        // 这里模板 删除了list以后的数据，也就是统计的这一行
//        String templateFileName =
//        this.getPath() + "demo" + File.separator + "fill" + File.separator + "complexFillWithTable.xlsx";
        String fileName = TestFileUtil.getPath() + "static" + File.separator + "t2.xlsx";

        ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build();
//        我这里不写sheetNo会有读不到的问题
        WriteSheet writeSheet = EasyExcel.writerSheet(1).build();
        List<FillData> list = new ArrayList<>();
        FillData fillData = new FillData();
        fillData.setDate(new Date());
        fillData.setCustomerName("测试");
        fillData.setNum(11);
        fillData.setProductName("测试产品");
        fillData.setOne(13.0);
        fillData.setTicketNo("15489");
        fillData.setType("c");
        fillData.setUnit("PCs");
        fillData.setCountOne(22.0);
        FillData fillData1 = new FillData();
        fillData1.setDate(new Date());
        fillData1.setCustomerName("测试1");
        fillData1.setNum(11);
        fillData1.setProductName("测试产品1");
        fillData1.setOne(13.0);
        fillData1.setTicketNo("154891");
        fillData1.setType("c1");
        fillData1.setUnit("PCs1");
        fillData1.setCountOne(22.0);
        list.add(fillData);
        list.add(fillData1);
// 这里注意 入参用了forceNewRow 代表在写入list的时候不管list下面有没有空行 都会创建一行，然后下面的数据往后移动。默认 是false，会直接使用下一行，如果没有则创建。
        // forceNewRow 如果设置了true,有个缺点 就是他会把所有的数据都放到内存了，所以慎用
        // 简单的说 如果你的模板有list,且list不是最后一行，下面还有数据需要填充 就必须设置 forceNewRow=true 但是这个就会把所有数据放到内存 会很耗内存
        // 如果数据量大 list不是最后一行 参照下一个
        FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
        // 直接写入数据
        excelWriter.fill(list, fillConfig, writeSheet);
//        excelWriter.fill(list, fillConfig, writeSheet);

//             写入list之前的数据
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderNo", "123456");
        map.put("count", 1000);
        excelWriter.fill(map, writeSheet);


        excelWriter.finish();
        // 总体上写法比较复杂 但是也没有想到好的版本 异步的去写入excel 不支持行的删除和移动，也不支持备注这种的写入，所以也排除了可以
        // 新建一个 然后一点点复制过来的方案，最后导致list需要新增行的时候，后面的列的数据没法后移，后续会继续想想解决方案
//        }

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
