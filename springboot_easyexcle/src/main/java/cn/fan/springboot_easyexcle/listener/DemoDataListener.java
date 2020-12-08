package cn.fan.springboot_easyexcle.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 这里采用适配器模式 来自动配置类型
 * @param <T>
 */

@Data
// 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
public class DemoDataListener<T> extends AnalysisEventListener<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoDataListener.class);
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
//    private static final int BATCH_COUNT = 5;
    List<T> list = new ArrayList<T>();
    List<Map> headList =new ArrayList<Map>();


    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
//    private DemoDAO demoDAO;
//    public DemoDataListener() {
//        // 这里是demo，所以随便new一个。实际使用如果到了spring,请使用下面的有参构造函数
//        demoDAO = new DemoDAO();
//    }
//    /**
//     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
//     *
//     * @param demoDAO
//     */
//    public DemoDataListener(DemoDAO demoDAO) {
//        this.demoDAO = demoDAO;
//    }
    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data
     *            one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(T data, AnalysisContext context) {

        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
//        if (list.size() >= BATCH_COUNT) {
//            saveData();
//            // 存储完成清理 list
//            list.clear();
//        }
    }
    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库

        LOGGER.info("所有数据解析完成！");
    }
    /**
     * 这里会一行行的返回头
     * 读取头需要加这个
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        headList.add(headMap);
        LOGGER.info("解析到一条头数据:{}", headMap);
    }
}