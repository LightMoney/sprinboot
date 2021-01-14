package cn.fan.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Document(collection = "collect")
@Data
public class CollectData {
    @Id
    private String id;

    private String date;

    private Integer equipId;

    private Integer productId;

    private List<ParamList> dataDetailVos;

    private Integer enterpriseId;

    private Integer useEnterpriseId;

    private Set<Integer> partnerId;

    private String terminalCode;


    @Data
    class ParamList {
        private Integer unitId;

        private String paramName;

        private Integer paramId;

        private BigDecimal value;

        private  Integer standardCode;

        private  String unitValue;
    }
}
