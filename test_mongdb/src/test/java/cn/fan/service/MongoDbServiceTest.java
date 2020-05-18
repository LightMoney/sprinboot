package cn.fan.service;

import cn.fan.model.Book;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
 public  class MongoDbServiceTest {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Test
    public void saveObj() {
//
//        log.info(""+mongoTemplate.findAll(Book.class));

        System.out.println("查询多条数据: 属于分级查询");
        Query query=new Query();
        //每页五个
        Pageable pageable=new PageRequest(1,1);
        query.with(pageable);
        //按sal排序
        query.with(new Sort(Sort.Direction.DESC,"price"));
        //查询总数
        Long count=mongoTemplate.count(query,Book.class,"book");
        List<Book> emps=mongoTemplate.find(query,Book.class);
        PageImpl<Book> page=  (PageImpl<Book>) PageableExecutionUtils.getPage(emps,pageable,()->count);
        log.info(""+ page);
    }


}