package cn.fan.plusgenerator;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

public class CodeGenerator {

    public static void main(String[] args) {
        //代码生成器
        AutoGenerator mpg = new AutoGenerator();

        //全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/plus-generator/src/main/java");
        gc.setAuthor("fan");
        gc.setServiceName("%sService");//自定义Service接口生成的文件名
        gc.setOpen(false);
        gc.setBaseResultMap(true);
        gc.setDateType(DateType.ONLY_DATE);
        mpg.setGlobalConfig(gc);

        //数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setUrl("jdbc:mysql://10.3.0.253:3306/product?useUnicode=true&characterEncoding=UTF8&allowMultiQueries=true");
//        dsc.setDriverName("com.mysql.jdbc.Driver");
//        dsc.setUsername("hthl");
//        dsc.setPassword("Hthl2018");

        dsc.setUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF8&allowMultiQueries=true");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        mpg.setDataSource(dsc);
//        dsc.setUrl("jdbc:mysql://172.18.105.33:3306/equip?useUnicode=true&characterEncoding=UTF8&allowMultiQueries=true");
//        dsc.setDriverName("com.mysql.jdbc.Driver");
//        dsc.setUsername("root");
//        dsc.setPassword("hthl@2018");
//        mpg.setDataSource(dsc);
        //包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("cn.fan.plusgenerator")
            .setMapper("dao");
        mpg.setPackageInfo(pc);

        //自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                //to do nothing
            }
        };

        //自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        //自定义配置会优先输出
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/plus-generator/src/main/resources/mapper/"
                        + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);


        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);


        //配置策略
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //strategy.setSuperControllerClass("com.example.demo.model.BaseEntity");
        strategy.setEntityLombokModel(false);//默认是false
        //strategy.setRestControllerStyle(true);
        //公共父类
        //strategy.setSuperControllerClass("com.example.demo.controller.BaseController");
        // 写于父类中的公共字段
        //strategy.setSuperEntityColumns("id");
//        strategy.setInclude("tb_account");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix("t_ba_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();

    }
}

