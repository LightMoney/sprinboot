package cn.fan.web;

import cn.fan.model.User;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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

    /**
     * 适用于单数据
     * 通过map传参
     * jasper studio中  ouline框下创建 parameters下对应参数  拖入对应位置
     * 代码中的字段名要和jasper中的字段名一致
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/test/param")
    public void creatPdf1(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServletOutputStream os = response.getOutputStream();
        //1.引入使用工具建好的jasper 文件
        Resource classPathResource = new ClassPathResource("template/param.jasper");
        FileInputStream inputStream = new FileInputStream(classPathResource.getFile());
//        2.创建jasperPrint，向jasper 中填充数据
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("username", "李四");
            map.put("age", "12");
            map.put("company", "光源科技");
            map.put("dept", "研发部");
            JasperPrint print = JasperFillManager.fillReport(inputStream, map, new JREmptyDataSource());
//            3.将 jasperPrint 输出 为pdf
            JasperExportManager.exportReportToPdfStream(print, os);
        } catch (JRException e) {
            e.printStackTrace();
        } finally {
            os.flush();
            os.close();
        }
    }


    /**
     * 适用于多数据list
     * jdbc 数据填充
     * jasper studio中 Repository Explorer 中Data Adapter 右键新建连接  选择jdbc连接
     * <p>
     * 选择对应驱动 输入用户名密码       Driver Classpath  引入对应的驱动包  测试连接成功后
     * 创建jasper工程，选择上面创建的连接，写sql完成fields的导入，拖进去就可以
     * 只保留后面有函数的部分呢   在detail区域操作  将区域高和fields的高设置为相同  生成的list行间无间隙
     * 选中对应的fields可在属性中设置border
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/test/sql")
    public void creatPdf2(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ServletOutputStream os = response.getOutputStream();
        //1.引入使用工具建好的jasper 文件
        Resource classPathResource = new ClassPathResource("template/jdbc.jasper");
        FileInputStream inputStream = new FileInputStream(classPathResource.getFile());
//        2.创建jasperPrint，向jasper 中填充数据
        try {
            Connection connection = getConnection();
            JasperPrint print = JasperFillManager.fillReport(inputStream, new HashMap<>(), connection);
//            3.将 jasperPrint 输出 为pdf
            JasperExportManager.exportReportToPdfStream(print, os);
        } catch (JRException e) {
            e.printStackTrace();
        } finally {
            os.flush();
            os.close();
        }
    }


    /**
     * jdbc 数据填充   动态传参
     * 适用于多数据list
     * <p>
     * jasper studio中 Repository Explorer 中Data Adapter 右键新建连接  选择jdbc连接
     * <p>
     * 选择对应驱动 输入用户名密码       Driver Classpath  引入对应的驱动包  测试连接成功后
     * 创建jasper工程，选择上面创建的连接，写sql  添加条件 where id=$P{cid}     完成fields的导入，拖进去就可以
     * 然后在Paramter中创建参数   cid
     * 只保留后面有函数的部分呢   在detail区域操作  将区域高和fields的高设置为相同  生成的list行间无间隙
     * 选中对应的fields可在属性中设置border
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/test/sql2")
    public void creatPdf3(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ServletOutputStream os = response.getOutputStream();
        //1.引入使用工具建好的jasper 文件
        Resource classPathResource = new ClassPathResource("template/jdbc2.jasper");
        FileInputStream inputStream = new FileInputStream(classPathResource.getFile());
//        2.创建jasperPrint，向jasper 中填充数据
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("cid", "1063705989926227968");
            Connection connection = getConnection();
            JasperPrint print = JasperFillManager.fillReport(inputStream, map, connection);
//            3.将 jasperPrint 输出 为pdf
            JasperExportManager.exportReportToPdfStream(print, os);
        } catch (JRException e) {
            e.printStackTrace();
        } finally {
            os.flush();
            os.close();
        }
    }

    public Connection getConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost/hrm?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8", "root", "root");
        return connection;
    }

    /**
     * javabean数据填充
     * <p>
     * 在outline  fields中创建对应的字段  与实体类中字段相同
     * 拉到jasper 界面即可
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/test/bean")
    public void creatPdf4(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ServletOutputStream os = response.getOutputStream();
        //1.引入使用工具建好的jasper 文件
        Resource classPathResource = new ClassPathResource("template/javabean.jasper");
        FileInputStream inputStream = new FileInputStream(classPathResource.getFile());
//        2.创建jasperPrint，向jasper 中填充数据
        try {
            HashMap<String, Object> map = new HashMap<>();
            List<User> users = new ArrayList<User>();
            for (int i = 0; i < 5; i++) {
                User user = new User();
                user.setName("colin");
                user.setAge("12");
                user.setCompany("光源科技");
                user.setDept("研发部");
                users.add(user);
            }
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(users);
            JasperPrint print = JasperFillManager.fillReport(inputStream, map, dataSource);
//            3.将 jasperPrint 输出 为pdf
            JasperExportManager.exportReportToPdfStream(print, os);
        } catch (JRException e) {
            e.printStackTrace();
        } finally {
            os.flush();
            os.close();
        }
    }

    /**
     * 分组 报表
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping("/test/group")
    public void creatPdf5(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ServletOutputStream os = response.getOutputStream();
        //1.引入使用工具建好的jasper 文件
        Resource classPathResource = new ClassPathResource("template/group.jasper");
        FileInputStream inputStream = new FileInputStream(classPathResource.getFile());
//        2.创建jasperPrint，向jasper 中填充数据
        try {
            HashMap<String, Object> map = new HashMap<>();
            List<User> users = new ArrayList<User>();
            for (int i = 0; i < 5; i++) {
                User user = new User();
                user.setName("colin");
                user.setAge("12");
                user.setCompany("光源科技");
                user.setDept("研发部");
                users.add(user);
            }

            for (int i = 0; i < 5; i++) {
                User user = new User();
                user.setName("li");
                user.setAge("12");
                user.setCompany("佳佳科技");
                user.setDept("研发部");
                users.add(user);
            }
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(users);
            JasperPrint print = JasperFillManager.fillReport(inputStream, map, dataSource);
//            3.将 jasperPrint 输出 为pdf
            JasperExportManager.exportReportToPdfStream(print, os);
        } catch (JRException e) {
            e.printStackTrace();
        } finally {
            os.flush();
            os.close();
        }
    }

    /**
     * 图形报表
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping("/test/chart")
    public void creatPdf6(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ServletOutputStream os = response.getOutputStream();
        //1.引入使用工具建好的jasper 文件
        Resource classPathResource = new ClassPathResource("template/chart.jasper");
        FileInputStream inputStream = new FileInputStream(classPathResource.getFile());
//        2.创建jasperPrint，向jasper 中填充数据
        try {
            HashMap<String, Object> map = new HashMap<>();
            List<User> users = new ArrayList<User>();
            for (int i = 0; i < 5; i++) {
                User user = new User();
                user.setName("colin");
                user.setAge("12");
                user.setCompany("光源科技");
                user.setDept("研发部");
                users.add(user);
            }

            for (int i = 0; i < 5; i++) {
                User user = new User();
                user.setName("li");
                user.setAge("12");
                user.setCompany("佳佳科技");
                user.setDept("研发部");
                users.add(user);
            }
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(users);
            JasperPrint print = JasperFillManager.fillReport(inputStream, map, dataSource);
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


