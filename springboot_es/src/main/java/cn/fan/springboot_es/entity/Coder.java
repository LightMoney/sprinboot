package cn.fan.springboot_es.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;

@Data
@Document(indexName = "persons", type = "coder")
public class Coder implements Serializable {
    @Id
    private Long id;
    @Field(type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_smart")
    private String codeName;

    private Integer age;

    private List<String> address = new ArrayList<>();

    private SubCoder subCoder = new SubCoder();

}
