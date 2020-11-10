package cn.fan.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
//import org.springframework.data.mongodb.core.mapping.Document;

@Data
//@Document(collection = "position_data")
@ApiModel("外勤 车辆 位置数据实体类")
public class PositionData  implements Serializable {
    //id由当前时间+出行编号组成
    @Id
    private String id;
    @ApiModelProperty("出行编号")
    private String taskId;
    @ApiModelProperty("当前时间")
    private Long createTime;
    @ApiModelProperty("格式化显示时间")
    private String showTime;
    @ApiModelProperty("企业id")
    private Integer enterpriseId;
    @ApiModelProperty("经度")
    private Double longitude;
    @ApiModelProperty("纬度")
    private Double latitude;
    @ApiModelProperty("mac值")
    private String macAddress;

}
