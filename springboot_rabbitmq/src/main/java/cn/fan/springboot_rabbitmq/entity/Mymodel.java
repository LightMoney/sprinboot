package cn.fan.springboot_rabbitmq.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * Auth Mr.fan
 * Date 2020/1/7 13:52
 **/
@Data
public class Mymodel implements Serializable {
    private static final  long serialVersionUID=1L;
    private UUID id;
    private String info;
}
