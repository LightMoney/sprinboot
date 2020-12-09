package cn.fan.springboot_easyexcle;

import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
 *         类名                            说明
 *
 * HSSFWorkbook           Excel的文档对象
 *
 * HSSFSheet                  Excel的表单
 *
 * HSSFRow                    Excel的行
 *
 * HSSFCell                     Excel的格子单元
 *
 * HSSFFont                    Excel字体
 *
 * HSSFDataFormat        格子单元的日期格式
 *
 * HSSFHeader               Excel文档Sheet的页眉
 *
 * HSSFFooter                Excel文档Sheet的页脚
 *
 * HSSFCellStyle            格子单元样式
 *
 * HSSFDateUtil             日期
 *
 * HSSFPrintSetup         打印
 *
 * HSSFErrorConstants   错误信息表
 */
public class PoiTest {
    /**
     * 获得对象
     */
    @Test
    public void testExcle(){
        try {
            //获取系统文档
//            POIFSFileSystem fspoi=new POIFSFileSystem(new FileInputStream("withHead.xlsx"));

            //创建工作薄对象
//            HSSFWorkbook workbook=new HSSFWorkbook(fspoi);
            XSSFWorkbook workbook=new XSSFWorkbook(new FileInputStream("withHead.xlsx"));
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
        HSSFWorkbook workbook=new HSSFWorkbook();//这里也可以设置sheet的Name
        //创建工作表对象
        HSSFSheet sheet = workbook.createSheet();
        //创建工作表的行
        HSSFRow row = sheet.createRow(0);//设置第一行，从零开始
//        创建单元格
        row.createCell(2).setCellValue("aaaaaaaaaaaa");//第一行第三列为aaaaaaaaaaaa
        row.createCell(0).setCellValue(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));//第一行第一列为日期
        workbook.setSheetName(0,"sheet的Name");//设置sheet的Name

//创建页眉和页脚
        HSSFHeader header =sheet.getHeader();//得到页眉
        header.setLeft("页眉左边");
        header.setRight("页眉右边");
        header.setCenter("页眉中间");
        HSSFFooter footer =sheet.getFooter();//得到页脚
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
        FileOutputStream out = new FileOutputStream( new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() +".xls");
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
     * @throws IOException
     */
    @Test
    public void testExcel4() throws IOException {
        //创建Excel工作薄对象
        HSSFWorkbook workbook=new HSSFWorkbook();
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
        FileOutputStream out = new FileOutputStream(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() +".xls");
        workbook.write(out);
        out.close();
    }

    //Excel的单元格操作
    @Test
    public void testExcel6() throws IOException {
        //创建Excel工作薄对象
        HSSFWorkbook workbook=new HSSFWorkbook();
        //创建Excel工作表对象
        HSSFSheet sheet = workbook.createSheet("wj");
        //创建行的单元格，从0开始
        HSSFRow row = sheet.createRow(0);
        //创建单元格
        HSSFCell cell=row.createCell(0);
        //设置值
        cell.setCellValue(new Date());
        //创建单元格样式
        HSSFCellStyle style=workbook.createCellStyle();
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
        cell.setCellStyle(style);
        //设置保留2位小数--使用Excel内嵌的格式
        HSSFCell cell1 = row.createCell(1);
        cell1.setCellValue(12.3456789);
        style=workbook.createCellStyle();
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        cell1.setCellStyle(style);
        //设置货币格式--使用自定义的格式
        HSSFCell cell2 = row.createCell(2);
        cell2.setCellValue(12345.6789);
        style=workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("￥#,##0"));
        cell2.setCellStyle(style);
        //设置百分比格式--使用自定义的格式
        HSSFCell cell3 = row.createCell(3);
        cell3.setCellValue(0.123456789);
        style=workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("0.00%"));
        cell3.setCellStyle(style);
        //设置中文大写格式--使用自定义的格式
        HSSFCell cell4 = row.createCell(4);
        cell4.setCellValue(12345);
        style=workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("[DbNum2][$-804]0"));
        cell4.setCellStyle(style);
        //设置科学计数法格式--使用自定义的格式
        HSSFCell cell5 = row.createCell(5);
        cell5.setCellValue(12345);
        style=workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("0.00E+00"));
        cell5.setCellStyle(style);

        //文档输出
        FileOutputStream out = new FileOutputStream( new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() +".xls");
        workbook.write(out);
        out.close();
    }

    //合并单元格(行列列是从0开始的)
    @Test
    public void testExcel7() throws IOException {
        //创建Excel工作薄对象
        HSSFWorkbook workbook=new HSSFWorkbook();
        //创建Excel工作表对象
        HSSFSheet sheet = workbook.createSheet("wj");
        //创建行的单元格，从0开始
        HSSFRow row = sheet.createRow(0);
        //创建单元格
        HSSFCell cell=row.createCell(0);
        //设置值
        cell.setCellValue(new Date());
        //合并列
        cell.setCellValue("合并列");
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cell.setCellStyle(cellStyle);
        CellRangeAddress region=new CellRangeAddress(0, 2, 0, 5);
        sheet.addMergedRegion(region);
        //合并行
        HSSFCell cell1 = row.createCell(6);
        cell1.setCellValue("合并行");
        region=new CellRangeAddress(0, 5, 6, 6);
        sheet.addMergedRegion(region);

        //文档输出
        FileOutputStream out = new FileOutputStream( new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() +".xls");
        workbook.write(out);
        out.close();
    }
}
