package cn.fan.testfunction.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Student implements Comparable {
    private  String name;
    private  Integer age;

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof  Student)) throw  new  RuntimeException("不是学生数据");
       return this.age-((Student) o).age;

    }
}
