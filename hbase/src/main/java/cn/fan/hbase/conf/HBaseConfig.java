//package cn.fan.hbase.conf;
//
//import org.apache.commons.collections.MapUtils;
//import org.apache.hadoop.hbase.client.Connection;
//import org.apache.hadoop.hbase.client.ConnectionFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.IOException;
//import java.util.Map;
//
//
///**
// * @author xyd
// * @Description hbase 配置类
// **/
//@Configuration
//@EnableConfigurationProperties(HBaseProperties.class)
//public class HBaseConfig {
//
//    private final static Logger logger = LoggerFactory.getLogger(HBaseConfig.class);
//
//    private final HBaseProperties properties;
//
//    public HBaseConfig(HBaseProperties properties) {
//        System.setProperty("HADOOP_USER_NAME", "hbase");
//        this.properties = properties;
//    }
//
//    @Bean(name = "connection")
//    public Connection configuration() {
//        org.apache.hadoop.conf.Configuration configuration = new org.apache.hadoop.conf.Configuration();
//        Map<String, String> config = properties.getConfig();
//        if (MapUtils.isNotEmpty(config)) {
//            for (String key : config.keySet()) {
//                configuration.set(key, config.get(key));
//            }
//        }
//        configuration.set("zookeeper.znode.parent", "/hbase");
//        configuration.setInt("hbase.rpc.timeout", 1800000);
//        configuration.setInt("hbase.client.operation.timeout", 1800000);
//        configuration.setInt("hbase.client.scanner.timeout.period", 1800000);
//        Connection connection = null;
//        try {
//            connection = ConnectionFactory.createConnection(configuration);
//            logger.info("hbaseConnection connect succeed");
//        } catch (IOException e) {
//            logger.warn("hbase 获取连接异常！", e);
//            try {
//                connection.close();
//            } catch (IOException e1) {
//                logger.warn("关闭hbase数据库连接异常！！！");
//            }
//        }
//        return connection;
//    }
//}