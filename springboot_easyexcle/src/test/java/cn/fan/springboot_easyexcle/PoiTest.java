package cn.fan.springboot_easyexcle;

import cn.fan.springboot_easyexcle.poi.handler.SheetHandler;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import org.springframework.util.StopWatch;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * HSSF提供读写Microsoft Excel XLS格式档案的功能。
 * XSSF提供读写Microsoft Excel OOXML XLSX格式档案的功能。
 * HWPF提供读写Microsoft Word DOC格式档案的功能。
 * HSLF提供读写Microsoft PowerPoint格式档案的功能。
 * HDGF提供读Microsoft Visio格式档案的功能。
 * HPBF提供读Microsoft Publisher格式档案的功能。
 * HSMF提供读Microsoft Outlook格式档案的功能。
 * 类名                            说明
 * <p>
 * HSSFWorkbook           Excel的文档对象
 * <p>
 * HSSFSheet                  Excel的表单
 * <p>
 * HSSFRow                    Excel的行
 * <p>
 * HSSFCell                     Excel的格子单元
 * <p>
 * HSSFFont                    Excel字体
 * <p>
 * HSSFDataFormat        格子单元的日期格式
 * <p>
 * HSSFHeader               Excel文档Sheet的页眉
 * <p>
 * HSSFFooter                Excel文档Sheet的页脚
 * <p>
 * HSSFCellStyle            格子单元样式
 * <p>
 * HSSFDateUtil             日期
 * <p>
 * HSSFPrintSetup         打印
 * <p>
 * HSSFErrorConstants   错误信息表
 */
public class PoiTest {
    /**
     * 获得对象
     */
    @Test
    public void testExcle() {
        try {
            //获取系统文档
//            POIFSFileSystem fspoi=new POIFSFileSystem(new FileInputStream("withHead.xlsx"));

            //创建工作薄对象
//            HSSFWorkbook workbook=new HSSFWorkbook(fspoi);
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream("withHead.xlsx"));
            //创建工作表对象
//            HSSFSheet sheet=workbook.getSheet("sheet1");
            XSSFSheet sheet = workbook.getSheet("sheet1");
            //得到Excel表格
//            HSSFRow row = sheet.getRow(1);
            XSSFRow row = sheet.getRow(1);
            //得到Excel工作表指定行的单元格
//            HSSFCell cell = row.getCell(1);
            XSSFCell cell = row.getCell(1);
            System.out.println(cell);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //创建Excel对象
    @Test
    public void testExcel2() throws IOException {
        //创建工作薄对象
        HSSFWorkbook workbook = new HSSFWorkbook();//这里也可以设置sheet的Name
        //创建工作表对象
        HSSFSheet sheet = workbook.createSheet();
        //创建工作表的行
        HSSFRow row = sheet.createRow(0);//设置第一行，从零开始
//        创建单元格
        row.createCell(2).setCellValue("aaaaaaaaaaaa");//第一行第三列为aaaaaaaaaaaa
        row.createCell(0).setCellValue(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));//第一行第一列为日期
        workbook.setSheetName(0, "sheet的Name");//设置sheet的Name

//创建页眉和页脚
        HSSFHeader header = sheet.getHeader();//得到页眉
        header.setLeft("页眉左边");
        header.setRight("页眉右边");
        header.setCenter("页眉中间");
        HSSFFooter footer = sheet.getFooter();//得到页脚
        footer.setLeft("页脚左边");
        footer.setRight("页脚右边");
        footer.setCenter("页脚中间");

        //创建文档信息
        workbook.createInformationProperties();
        //获取DocumentSummaryInformation对象
        DocumentSummaryInformation documentSummaryInformation = workbook.getDocumentSummaryInformation();
        documentSummaryInformation.setCategory("类别:Excel文件");//类别
        documentSummaryInformation.setManager("管理者:王军");//管理者
        documentSummaryInformation.setCompany("公司:Action");//公司


        //文档输出
        FileOutputStream out = new FileOutputStream(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() + ".xls");
        workbook.write(out);
        out.close();
    }

    //创建批注

    /**
     * dx1 第1个单元格中x轴的偏移量
     * dy1 第1个单元格中y轴的偏移量
     * dx2 第2个单元格中x轴的偏移量
     * dy2 第2个单元格中y轴的偏移量
     * col1 第1个单元格的列号
     * row1 第1个单元格的行号
     * col2 第2个单元格的列号
     * row2 第2个单元格的行号
     *
     * @throws IOException
     */
    @Test
    public void testExcel4() throws IOException {
        //创建Excel工作薄对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建Excel工作表对象
        HSSFSheet sheet = workbook.createSheet("wj");
        HSSFPatriarch patr = sheet.createDrawingPatriarch();
        //创建批注位置(row1-row3:直接理解为高度，col1-col2：直接理解为宽度)
        HSSFClientAnchor anchor = patr.createAnchor(0, 0, 0, 0, 5, 1, 8, 3);
        //创建批注
        HSSFComment comment = patr.createCellComment(anchor);
        //设置批注内容
        comment.setString(new HSSFRichTextString("这是一个批注段落！"));
        //设置批注作者
        comment.setAuthor("wangjun");
        //设置批注默认显示
        comment.setVisible(true);
        HSSFCell cell = sheet.createRow(2).createCell(1);
        cell.setCellValue("测试");
        //把批注赋值给单元格
        cell.setCellComment(comment);

        //文档输出
        FileOutputStream out = new FileOutputStream(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() + ".xls");
        workbook.write(out);
        out.close();
    }

    //Excel的单元格操作
    @Test
    public void testExcel6() throws IOException {
        //创建Excel工作薄对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建Excel工作表对象
        HSSFSheet sheet = workbook.createSheet("wj");
        //创建行的单元格，从0开始
        HSSFRow row = sheet.createRow(0);
        //创建单元格
        HSSFCell cell = row.createCell(0);
        //设置值
        cell.setCellValue(new Date());
        //创建单元格样式
        HSSFCellStyle style = workbook.createCellStyle();
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
        cell.setCellStyle(style);
        //设置保留2位小数--使用Excel内嵌的格式
        HSSFCell cell1 = row.createCell(1);
        cell1.setCellValue(12.3456789);
        style = workbook.createCellStyle();
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        cell1.setCellStyle(style);
        //设置货币格式--使用自定义的格式
        HSSFCell cell2 = row.createCell(2);
        cell2.setCellValue(12345.6789);
        style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("￥#,##0"));
        cell2.setCellStyle(style);
        //设置百分比格式--使用自定义的格式
        HSSFCell cell3 = row.createCell(3);
        cell3.setCellValue(0.123456789);
        style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("0.00%"));
        cell3.setCellStyle(style);
        //设置中文大写格式--使用自定义的格式
        HSSFCell cell4 = row.createCell(4);
        cell4.setCellValue(12345);
        style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("[DbNum2][$-804]0"));
        cell4.setCellStyle(style);
        //设置科学计数法格式--使用自定义的格式
        HSSFCell cell5 = row.createCell(5);
        cell5.setCellValue(12345);
        style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("0.00E+00"));
        cell5.setCellStyle(style);

        //文档输出
        FileOutputStream out = new FileOutputStream(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() + ".xls");
        workbook.write(out);
        out.close();
    }

    //合并单元格(行列列是从0开始的)
    @Test
    public void testExcel7() throws IOException {
        //创建Excel工作薄对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建Excel工作表对象
        HSSFSheet sheet = workbook.createSheet("wj");
        //创建行的单元格，从0开始
        HSSFRow row = sheet.createRow(0);
        //创建单元格
        HSSFCell cell = row.createCell(0);
        //设置值
        cell.setCellValue(new Date());
        //合并列
        cell.setCellValue("合并列");
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cell.setCellStyle(cellStyle);
        CellRangeAddress region = new CellRangeAddress(0, 2, 0, 5);
        sheet.addMergedRegion(region);
        //合并行
        HSSFCell cell1 = row.createCell(6);
        cell1.setCellValue("合并行");
        region = new CellRangeAddress(0, 5, 6, 6);
        sheet.addMergedRegion(region);

        //文档输出
        FileOutputStream out = new FileOutputStream(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() + ".xls");
        workbook.write(out);
        out.close();
    }


    @Test
    public void tets1() throws IOException {
        //创建Excel工作薄对象
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Test");// 创建工作表(Sheet)
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellFormula("2+3*4");//设置公式
        cell = row.createCell(1);
        cell.setCellValue(10);
        cell = row.createCell(2);
        cell.setCellFormula("SUM(A1:B1)");//设置公式

        FileOutputStream out = new FileOutputStream(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() + ".xls");
        workbook.write(out);
        out.close();
    }

    @Test
    public void start1() throws IOException {
        //1.创建工作簿  HSSFWorkbook -- 2003
        Workbook wb = new XSSFWorkbook(); //2007版本
        //2.创建表单sheet
        Sheet sheet = wb.createSheet("test");
        //3.文件流
        FileOutputStream pis = new FileOutputStream("E:\\excel\\poi\\test.xlsx");
        //4.写入文件
        wb.write(pis);
        pis.close();
    }

    /**
     * 创建单元格写入内容
     */
    @Test
    public void start2() throws IOException {
        //创建工作簿  HSSFWorkbook -- 2003
        Workbook wb = new XSSFWorkbook(); //2007版本
        //创建表单sheet
        Sheet sheet = wb.createSheet("test");
        //创建行对象  参数：索引（从0开始）
        Row row = sheet.createRow(2);
        //创建单元格对象  参数：索引（从0开始）
        Cell cell = row.createCell(2);
        //向单元格中写入内容
        cell.setCellValue("传智播客");
        //文件流
        FileOutputStream pis = new FileOutputStream("E:\\excel\\poi\\test1.xlsx");
        //写入文件
        wb.write(pis);
        pis.close();
    }

    /**
     * 单元格样式处理
     */
    @Test
    public void start3() throws IOException {
        //创建工作簿  HSSFWorkbook -- 2003
        Workbook wb = new XSSFWorkbook(); //2007版本
        //创建表单sheet
        Sheet sheet = wb.createSheet("test");
        //创建行对象  参数：索引（从0开始）
        Row row = sheet.createRow(2);
        //创建单元格对象  参数：索引（从0开始）
        Cell cell = row.createCell(2);
        //向单元格中写入内容
        cell.setCellValue("传智播客");

        //样式处理
        //创建样式对象
        CellStyle style = wb.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);//上边框
        style.setBorderBottom(BorderStyle.THIN);//下边框
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setBorderRight(BorderStyle.THIN);//右边框
        //创建字体对象
        Font font = wb.createFont();
        font.setFontName("华文行楷"); //字体
        font.setFontHeightInPoints((short) 28);//字号
        style.setFont(font);

        //行高和列宽
        row.setHeightInPoints(50);//行高
        //列宽的宽度  字符宽度
        sheet.setColumnWidth(2, 31 * 256);//列宽

        //剧中显示
        style.setAlignment(HorizontalAlignment.CENTER);//水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中

        //向单元格设置样式
        cell.setCellStyle(style);

        //文件流
        FileOutputStream pis = new FileOutputStream("E:\\excel\\poi\\test2.xlsx");
        //写入文件
        wb.write(pis);
        pis.close();
    }

    /**
     * 插入图片
     */
    @Test
    public void start4() throws IOException {
        //创建工作簿  HSSFWorkbook -- 2003
        Workbook wb = new XSSFWorkbook(); //2007版本
        //创建表单sheet
        Sheet sheet = wb.createSheet("test");

        //读取图片流
        FileInputStream stream = new FileInputStream("E:\\excel\\poi\\logo.jpg");
        //转化二进制数组
        byte[] bytes = IOUtils.toByteArray(stream);
        stream.read(bytes);
        //向POI内存中添加一张图片，返回图片在图片集合中的索引
        int index = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);//参数一：图片的二进制数据，参数二：图片类型
        //绘制图片工具类
        CreationHelper helper = wb.getCreationHelper();
        //创建一个绘图对象
        Drawing<?> patriarch = sheet.createDrawingPatriarch();
        //创建锚点，设置图片坐标
        ClientAnchor anchor = helper.createClientAnchor();
        anchor.setRow1(0);
        anchor.setCol1(0);
        //绘制图片
        Picture picture = patriarch.createPicture(anchor, index);//图片位置，图片的索引
        picture.resize();//自适应渲染图片

        //文件流
        FileOutputStream pis = new FileOutputStream("E:\\excel\\poi\\test3.xlsx");
        //写入文件
        wb.write(pis);
        pis.close();
    }

    /**
     * 读取文档并解析
     */
   @Test
    public void  start5() throws IOException {
       //1.根据Excel文件创建工作簿
       Workbook wb = new XSSFWorkbook("E:\\excel\\poi\\demo.xlsx");
       //2.获取Sheet
       Sheet sheet = wb.getSheetAt(0);//参数：索引
       //3.获取Sheet中的每一行，和每一个单元格
       for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
           Row row = sheet.getRow(rowNum);//根据索引获取每一个行
           StringBuilder sb = new StringBuilder();
           for (int cellNum = 2; cellNum < row.getLastCellNum(); cellNum++) {
               //根据索引获取每一个单元格
               Cell cell = row.getCell(cellNum);
               //获取每一个单元格的内容
               Object value = getCellValue(cell);
               sb.append(value).append("-");
           }
           System.out.println(sb.toString());
       }
   }
    public static Object getCellValue(Cell cell) {
        //1.获取到单元格的属性类型
        CellType cellType = cell.getCellTypeEnum();
        //2.根据单元格数据类型获取数据
        Object value = null;
        switch (cellType) {
            case STRING:
                value = cell.getStringCellValue();
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case NUMERIC:
                if(DateUtil.isCellDateFormatted(cell)) {
                    //日期格式
                    value = cell.getDateCellValue();
                }else{
                    //数字
                    value = cell.getNumericCellValue();
                }
                break;
            case FORMULA: //公式
                value = cell.getCellFormula();
                break;
            default:
                break;
        }
        return value;
    }
    /**
     * 使用事件模型解析百万数据excel报表
     */
    @Test
    public void start6() throws IOException, SAXException, OpenXML4JException {
        StopWatch watch = new StopWatch();
        watch.start();
        String path = "D:\\BaiduNetdiskDownload\\26-传统行业SaaS解决方案\\08-员工管理及POI\\02-POI报表的高级应用\\资源\\百万数据报表\\demo.xlsx";

        //1.根据excel报表获取OPCPackage
        OPCPackage opcPackage = OPCPackage.open(path, PackageAccess.READ);
        //2.创建XSSFReader
        XSSFReader reader = new XSSFReader(opcPackage);
        //3.获取SharedStringTable对象
        ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(
                opcPackage);
//        SharedStringsTable table = reader.getSharedStringsTable();
        //4.获取styleTable对象
        StylesTable stylesTable = reader.getStylesTable();
        //5.创建Sax的xmlReader对象
        XMLReader xmlReader = XMLReaderFactory.createXMLReader();
        //6.注册事件处理器
        XSSFSheetXMLHandler xmlHandler = new XSSFSheetXMLHandler(stylesTable,strings,new SheetHandler(),false);
        xmlReader.setContentHandler(xmlHandler);
        //7.逐行读取
        XSSFReader.SheetIterator sheetIterator = (XSSFReader.SheetIterator) reader.getSheetsData();
        while (sheetIterator.hasNext()) {
            InputStream stream = sheetIterator.next(); //每一个sheet的流数据
            InputSource is = new InputSource(stream);
            xmlReader.parse(is);
        }
        watch.stop();
        System.out.println(watch.getTotalTimeSeconds());
    }
}
