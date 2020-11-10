package cn.fan.springbootcache;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class GoodsVO  implements Serializable {
    private  String goodsId;
    private Integer goodsType;
    private  String goodsCode;
    private  String goodsName;

}
