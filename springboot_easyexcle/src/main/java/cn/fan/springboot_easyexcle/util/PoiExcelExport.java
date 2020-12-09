package cn.fan.springboot_easyexcle.util;



import cn.fan.springboot_easyexcle.model.ExcelData;
import com.alibaba.excel.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;


@Slf4j
public class PoiExcelExport {

    public static void exportExcel(HttpServletRequest request, HttpServletResponse response, ExcelData data) {
        log.info("导出解析开始，fileName:{}", data.getFileName());
        try {
            //实例化HSSFWorkbook
            HSSFWorkbook workbook = fillExcelWorkbook(data);
            //设置浏览器下载
            setBrowser(request, response, workbook, data.getFileName());
            log.info("导出解析成功!");
        } catch (Exception e) {
            log.info("导出解析失败!", e);
        }
    }

    /**
     * @return 字节数组
     * @Description 返回导出excel的字节流
     * @Param [data] exceldata
     **/
    public static byte[] exportExcel(ExcelData data, String type) {
        log.info("导出解析开始，fileName:{}", data.getFileName());
        //实例化HSSFWorkbook
        HSSFWorkbook workbook = fillExcelWorkbook(data);
        log.info("导出解析成功!");
        String filePath = saveExcelFile(workbook, type);
        if (StringUtils.isEmpty(filePath)) {
            return null;
        }
        return FileDownLoadUtil.download(filePath);
    }


    /**
     * 保存临时文件
     *
     * @param workbook 表格数据
     * @return 路径
     */
    public static String saveExcelFile(HSSFWorkbook workbook, String type) {
        String rootPath = "";
        if (File.separator.equals("\\")) {
            rootPath = "C:";
        }
        log.info("根路径：{}", rootPath);
        StringBuilder sb = new StringBuilder();
        sb.append(rootPath).append(File.separator).append("download").append(File.separator).append(type);
        checkPathExist(sb.toString());
        sb.append(File.separator).append("temp-").append(System.currentTimeMillis()).append("-").append(UUID.randomUUID()).append(
                ".xls");
        log.info("文件路径：{}", sb.toString());
        try {
            FileOutputStream output = new FileOutputStream(sb.toString());
            workbook.write(output);
        } catch (IOException e) {
            log.warn("生成excel文件异常！！！");
            return null;
        }
        return sb.toString();
    }

    /**
     * 判断文件夹是否存在，如果不存在则新建
     *
     * @param dirPath 文件夹路径
     */
    private static void checkPathExist(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * @return workbook
     * @Description 填充excel
     * @Param [data] exceldata
     **/
    public static HSSFWorkbook fillExcelWorkbook(ExcelData data) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建一个Excel表单，参数为sheet的名字
        HSSFSheet sheet = workbook.createSheet("sheet");
        //设置表头
        setTitle(workbook, sheet, data.getHeads());
        //设置单元格并赋值
        setData(sheet, data.getList(), data.getCols());
        //设置合并单元格的内容
        setMerge(workbook,sheet, data.getMergeMap());
        return workbook;
    }


    /**
     * 设置合并单元格
     * @param sheet excel sheet 支持指定sheet 的单元格内容合并
     * @param mergeMap 待合并的数据格式  key  为合并的行数列数 结构 firstRow-lastRow-firstCell-lastCell  value 合并后的单元格插入的内容
     */
    private static void setMerge(HSSFWorkbook workbook,HSSFSheet sheet,Map<String,String> mergeMap){
        if(MapUtils.isEmpty(mergeMap)){
            return;
        }
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        Set<Map.Entry<String,String>> set = mergeMap.entrySet();
        set.forEach(s -> {
            String key =s.getKey();
            if(key.split("-").length != 4){
                return;
            }
            String[] keys = key.split("-");
            CellRangeAddress callRangeAddress = new CellRangeAddress(Integer.parseInt(keys[0]),
                    Integer.parseInt(keys[1]),
                    Integer.parseInt(keys[2]), Integer.parseInt(keys[3]));
            sheet.addMergedRegion(callRangeAddress);
            HSSFRow row = sheet.getRow(Integer.parseInt(keys[0]));
            HSSFCell cell = row.getCell(Integer.parseInt(keys[2]));
            cell.setCellValue(s.getValue());
            cell.setCellStyle(style);
                }
        );
    }

    /**
     * 方法名：setTitle
     * 功能：设置表头
     */
    private static void setTitle(HSSFWorkbook workbook, HSSFSheet sheet, String[] str) {
        try {
            HSSFRow row = sheet.createRow(0);
            //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
            for (int i = 0; i <= str.length; i++) {
                sheet.setColumnWidth(i, 15 * 256);
            }
            //设置为居中加粗,格式化时间格式
            HSSFCellStyle style = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
            //创建表头名称
            HSSFCell cell;
            for (int j = 0; j < str.length; j++) {
                cell = row.createCell(j);
                cell.setCellValue(str[j]);
                cell.setCellStyle(style);
            }
        } catch (Exception e) {
            log.info("导出时设置表头失败！", e);
        }
    }

    /**
     * 方法名：setData
     * 功能：表格赋值
     */
    private static void setData(HSSFSheet sheet, List<T> list, String[] cols) {
        try {
            BeanToMap<T> btm = new BeanToMap<>();
            for (int rowNum = 1; rowNum <= list.size(); rowNum++) {
                Map<String, Object> hm = btm.getMap(list.get(rowNum - 1));
                HSSFRow row = sheet.createRow(rowNum);
                // 读取数据值
                for (int k = 0; k < cols.length; k++) {
                    HSSFCell hssfcell = row.createCell(k);
                    Object obj = hm.get(cols[k]);
                    if (null == obj) {
                        hssfcell.setCellValue("");
                        continue;
                    }
                    if (obj instanceof Date) {
                        hssfcell.setCellValue(DateFormateUtils.date2String((Date) obj, "yyyy-MM-dd HH:mm:ss"));
                        continue;
                    }
                    hssfcell.setCellValue(obj.toString());
                }
            }
            log.info("表格赋值成功！");
        } catch (Exception e) {
            log.info("表格赋值失败！", e);
        }
    }

    /**
     * 方法名：setBrowser
     * 功能：使用浏览器下载
     */
    private static void setBrowser(HttpServletRequest request, HttpServletResponse response, HSSFWorkbook workbook,
                                   String fileName) {
        try {
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            //将excel写入到输出流中
            workbook.write(os);
            os.flush();
            os.close();
            log.info("设置浏览器下载成功！");
        } catch (Exception e) {
            log.info("设置浏览器下载失败！", e);
        }
    }

    /**
     * 导出Excel
     *
     * @param sheetName sheet名称
     * @param title     标题
     * @param values    内容
     * @param wb        HSSFWorkbook对象
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] values, HSSFWorkbook wb, String mergeCell, Integer cellNum) {

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null) {
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        //声明列对象
        HSSFCell cell = null;

        if (!StringUtils.isEmpty(mergeCell)) {

            //合并单元格
            CellRangeAddress callRangeAddress = new CellRangeAddress(0, 0, 0, cellNum);
            sheet.addMergedRegion(callRangeAddress);
            cell = row.createCell(0);
            cell.setCellValue(mergeCell);
            cell.setCellStyle(style);


            row = sheet.createRow(1);
            //创建标题
            for (int i = 0; i < title.length; i++) {
                cell = row.createCell(i);
                cell.setCellValue(title[i]);
                cell.setCellStyle(style);
            }

            //创建内容
            for (int i = 0; i < values.length; i++) {
                row = sheet.createRow(i + 2);
                for (int j = 0; j < values[i].length; j++) {
                    //将内容按顺序赋给对应的列对象
                    row.createCell(j).setCellValue(values[i][j]);
                }
            }
        } else {
            //创建标题
            for (int i = 0; i < title.length; i++) {
                cell = row.createCell(i);
                cell.setCellValue(title[i]);
                cell.setCellStyle(style);
            }

            //创建内容
            for (int i = 0; i < values.length; i++) {
                row = sheet.createRow(i + 1);
                for (int j = 0; j < values[i].length; j++) {
                    //将内容按顺序赋给对应的列对象
                    row.createCell(j).setCellValue(values[i][j]);
                }
            }

        }
        return wb;
    }
}
