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
//
//    public List<String> getAddress() {
//        return address;
//    }
//
//    public void setAddress(List<String> address) {
//        this.address = address;
//    }
//
//    public SubCoder getSubCoder() {
//        return subCoder;
//    }
//
//    public void setSubCoder(SubCoder subCoder) {
//        this.subCoder = subCoder;
//    }
//
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Integer getAge() {
//        return age;
//    }
//
//    public void setAge(Integer age) {
//        this.age = age;
//    }
}
