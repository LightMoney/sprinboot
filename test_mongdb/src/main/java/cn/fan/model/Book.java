package cn.fan.model;

import java.util.Date;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="book")
@Data
public class Book {

	@Id
	private String id;
	private Integer price;
	private String name;
	private String info;
	private String publish;
	private Date createTime;
	private Date updateTime;
}