package cn.fan.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "position_data")
public class PositionData {
    //id由当前时间+出行编号组成
    @Id
    private String id;
//    @ApiModelProperty("出行编号")
    private String taskId;
//    @ApiModelProperty("当前时间")
    private Long createTime;
//    @ApiModelProperty("格式化显示时间")
    private String showTime;
//    @ApiModelProperty("企业id")
    private Integer enterpriseId;
//    @ApiModelProperty("经度")
    private Double longitude;
//    @ApiModelProperty("纬度")
    private Double latitude;
//    @ApiModelProperty("mac值")
    private String macAddress;

}