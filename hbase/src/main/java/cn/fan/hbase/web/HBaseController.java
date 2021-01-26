package cn.fan.hbase.web;

import cn.fan.hbase.service.HBaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Slf4j
public class HBaseController {
    @Autowired
    private HbaseTemplate hbaseTemplate;
    @Autowired
    private HBaseService service;

    /**
     * 通过表名，开始行键和结束行键获取数据
     * 通过列名，列族名过滤
     *
     * @return
     */
    @GetMapping("/row")
    public List<Map<String, Object>> getRow() {
        Scan scan = new Scan();
        String column = "";
//        String qualifier = "equipId";//"userId";
        String startRow = null;//"100036113_9223370445513586715";
        String stopRow = null;//"100036113_9223370445513587922";
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        filterList.addFilter(new RowFilter(CompareFilter.CompareOp.EQUAL,new RegexStringComparator(163 + "_.*")));
        if (StringUtils.isNotBlank(column)) {
            log.debug("{}", column);
            //匹配列族名相同的显示
            filterList.addFilter(new FamilyFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(column))));
        }
//        if (StringUtils.isNotBlank(qualifier)) {
//            log.debug("{}", qualifier);
//            //匹配列名相同的字段显示
//            filterList.addFilter(new QualifierFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(qualifier))));
//        }
        //显示的数据量，可以设置一个默认值，避免查询数据过多导致缓慢
        PageFilter page = new PageFilter(2);
        scan.setFilter(page);
        if (filterList.getFilters().size() > 0) {
            filterList.addFilter(page);
            scan.setFilter(filterList);
        }

        if (startRow == null) {
            startRow = "";
        }
        if (stopRow == null) {
            stopRow = "";
        }
//        scan.setStartRow(Bytes.toBytes(startRow));
//        scan.setStopRow(Bytes.toBytes(stopRow));
        /* PageFilter filter = new PageFilter(5);
         scan.setFilter(filter);*/
        return hbaseTemplate.find("test:hy_standard", scan, new RowMapper<Map<String, Object>>() {
            @Override
            public Map<String, Object> mapRow(Result result, int rowNum) throws Exception {
                List<Cell> ceList = result.listCells();
                Map<String, Object> map = new HashMap<String, Object>();
                Map<String, Map<String, Object>> returnMap = new HashMap<String, Map<String, Object>>();
                String row = "";
                if (ceList != null && ceList.size() > 0) {
                    for (Cell cell : ceList) {
                        row = Bytes.toString(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());
                        String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                        String family = Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength());
                        String quali = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
//                        String key = Bytes.toString(CellUtil.cloneQualifier(p));
//                        String value = Bytes.toString(CellUtil.cloneValue(p));
                        map.put(family + "_" + quali, value);
                    }
                    map.put("row", row);
                }
                return map;
            }
        });
    }


    /**
     * 通过表名和key获取一行数据
     *
     * @return
     */
    @GetMapping("/row1")
    public Map<String, Object> getRow1() {
        Map<String, Object> map = hbaseTemplate.get("test:water_equip", "100036113_9223370445513586715", (result, rowNum) -> {
            List<Cell> listCell = result.listCells();
            Map<String, Object> cellMap = new HashMap<String, Object>(16);
            if (listCell != null && listCell.size() > 0) {
                for (Cell cell : listCell) {
                    cellMap.put(Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength()) +
                                    "_" + Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength()),
                            Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
                }
            }
            return cellMap;
        });
        //补全查询的ID
        map.put("id", "100036113_9223370445513586715");
        return map;
    }

    /**
     * 通过表名  key 和 列族 和列 获取一个数据
     *
     * @param tableName
     * @param rowName
     * @param familyName
     * @param qualifier
     * @return
     */
    public String get(String tableName, String rowName, String familyName, String qualifier) {
        return hbaseTemplate.get(tableName, rowName, familyName, qualifier, new RowMapper<String>() {
            @Override
            public String mapRow(Result result, int i) throws Exception {
                List<Cell> ceList = result.listCells();
                String res = "";
                if (ceList != null && ceList.size() > 0) {
                    for (Cell cell : ceList) {
                        res = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                    }
                }
                return res;
            }
        });
    }

    /**
     * 数据插入
     *
     * @param tableName 表名
     * @author : zhangai
     * @date : 11:42 2018/6/22
     * @description：
     */
    public Boolean execute(String tableName) {
        return hbaseTemplate.execute(tableName, (hTableInterface) -> {
            boolean flag = false;
            try {
                Put put = new Put("123".getBytes());
                hTableInterface.put(put);
                flag = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return flag;
        });
    }

//    private Scan rebuildScan(HistoryCondition condition) {
//        Integer equipId = condition.getEquipId();
//        Long startTime = condition.getStartTime();
//        Long endTime = condition.getEndTime();
//        String startRow = condition.getStartRow();
//        String endRow = condition.getEndRow();
//        Integer enterPriseId = LoginContextHolder.getCurEnterPriseId();
//        Scan scan = new Scan();
//        if (StringUtils.isNotBlank(endRow)) {
//            scan.setStopRow(Bytes.toBytes(endRow));
//        } else if (startTime != null) {
//            long s = Long.MAX_VALUE - startTime;
//            scan.setStopRow(Bytes.toBytes(enterPriseId+""+equipId + "_" + s));
//        }
//
//        if (StringUtils.isNotBlank(startRow)) {
//            scan.setStartRow(Bytes.toBytes(startRow));
//        } else if (endTime != null) {
//            long s = Long.MAX_VALUE - endTime;
//            scan.setStartRow(Bytes.toBytes(enterPriseId+""+equipId + "_" + s));
//        }
//        scan.setFilter(rebuildFilterCondition(condition));
//        return scan;
//    }
//
//    private FilterList rebuildFilterCondition(HistoryCondition condition) {
//        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
//        Filter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator(condition.getEquipId() + "_.*"));
//        filterList.addFilter(filter);
//        Integer cPageSize = condition.getPageSize();
//        int pageSize = (cPageSize != null) ? cPageSize : EQUIP_DEFAULT_PAGE_SIZE;
//        Filter pageFilter = new PageFilter(pageSize + 1);
//        filterList.addFilter(pageFilter);
//        return filterList;
//    }
}

