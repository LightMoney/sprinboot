//package cn.fan.springboot_easyexcle.importdemo;
//
//import com.hthl.context.LoginContextHolder;
//import com.hthl.framework.domain.equips.equip.Equip;
//import com.hthl.framework.domain.equips.equip.vo.EquipTemplate;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BatchPreparedStatementSetter;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
//@Component
//@Slf4j
//public class EquipImportExecutor {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    private static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
//            10, 10, 60, TimeUnit.SECONDS,
//            new LinkedBlockingQueue<>(20), new ThreadPoolExecutor.DiscardPolicy());
//
//
//    public void execute(List<EquipTemplate> templates) {
//        int size = templates.size();
//        int start = 0;
//        int end = start + 1000;
//
//        while (start < size) {
//            if (end > size) {
//                end = size;
//            }
//            List<EquipTemplate> equipTemplates = templates.subList(start, end);
//            poolExecutor.execute(new EquipImportTemplate(equipTemplates, LoginContextHolder.getCurEnterPriseId()));
//            start = end;
//            end = start + 1000;
//        }
//
//
//    }
//
//    /**
//     * 批量插入（部分字段）
//     *
//     * @param equips 设备集合
//     * @return 返回本次批次号
//     */
//    public boolean batchInsert(List<Equip> equips) {
//        log.info("批量插入设备数目，size:{}", equips);
//        long t1 = System.currentTimeMillis();
////fixme  确定模型后再去设置唯一主键  如果找到就更新 update后面的值
//        String sql = "insert into t_e_equip (enterprise_id,equip_code,equip_name,create_time)" +
//                " values (?,?,?,?) ON DUPLICATE " +
//                "KEY UPDATE " +
//                "`equip_name` = VALUES" +
//                "(`equip_name`) ";
//        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
//
//            @Override
//            public int getBatchSize() {
//                return equips.size();
//            }
//
//            @Override
//            public void setValues(PreparedStatement ps, int i)
//                    throws SQLException {
//                ps.setInt(1, equips.get(i).getEnterpriseId());
//                ps.setString(2, equips.get(i).getEquipCode());
//                ps.setString(3, equips.get(i).getEquipName());
//                ps.setDate(4, new java.sql.Date(System.currentTimeMillis()));
//            }
//        });
//        long t2 = System.currentTimeMillis();
//        log.info("耗时：{}", (t2 - t1));
//        return true;
//    }
//
//    private class EquipImportTemplate implements Runnable {
//
//        List<EquipTemplate> templates;
//        Integer enpriseId;//多线程无法获取企业id
//
//        public EquipImportTemplate(List<EquipTemplate> templates, Integer enpriseId) {
//            this.templates = templates;
//            this.enpriseId = enpriseId;
//        }
//
//        @Override
//        public void run() {
//            log.info("线程：{} 开始执行......", Thread.currentThread().getName());
//            List<Equip> equips = buildEquip(templates);
//            boolean batchInsert = batchInsert(equips);
//            if (!batchInsert){
//                log.warn("------------插入失败----------");
//            }
//            log.info("线程：{} 执行结束......", Thread.currentThread().getName());
//        }
//
//        private List<Equip> buildEquip(List<EquipTemplate> templates) {
//            if (CollectionUtils.isEmpty(templates)) {
//                return Collections.emptyList();
//            }
//            List<Equip> equips = new ArrayList<>();
//            templates.forEach(p -> {
//                Equip equip = p.buildEquip();
////                equip.setTypeId(typeId);
//                equip.setEnterpriseId(enpriseId);
//                equip.setCreateTime(new Date());
//                equips.add(equip);
//            });
//            return equips;
//        }
//    }
//}
