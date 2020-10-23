package cn.fan.service;

import cn.fan.model.Book;

import cn.fan.util.MongoPageHelper;
import cn.fan.util.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.internal.requests.ClassRequest;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MongoDbServiceTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Resource
    private MongoPageHelper helper;

    @Test
    public void testObj() {
//
//        log.info(""+mongoTemplate.findAll(Book.class));

        System.out.println("查询多条数据: 属于分级查询");
        Query query = new Query();
        //每页五个
        Pageable pageable = new PageRequest(1, 2);
        query.with(pageable);

        //按sal排序
//        query.with(new Sort(Sort.Direction.DESC,"price"));
        //查询总数
        long count = 0;//=mongoTemplate.count(query,Book.class,"book");
        List<Book> emps = mongoTemplate.find(query, Book.class);
        log.info("" + emps);
        PageImpl<Book> page = (PageImpl<Book>) PageableExecutionUtils.getPage(emps, pageable, () -> count);
        log.info("" + page);
        System.out.println("" + page);
    }


//    @Test
//    public  List<ClassRequest> list(){
//        Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(Criteria.where("college").is("计算机学院")),
//                Aggregation.group("className","college").count().as("count")
//                        .max("age").as("max")
//                        .min("age").as("min")
//                ,Aggregation.project().and("className").as("name").andInclude("college","count","max","min")
//        );
//        AggregationResults<ClassRequest> aggregationResults =
//                mongoTemplate.aggregate(aggregation, Book.class, ClassRequest.class);
//
//        return aggregationResults.getMappedResults();
//    }

    @Test
    public void saveObj() {
        //没有设置@id的值时，会自动生成一个序列号  (_id)
        Book book=new Book();
        book.setId("122222");
        book.setName("测试2244");
        book.setCreateTime(new Date());
        book.setTestTime(Long.valueOf("1603414701000"));

//        当确定了id主键时似乎有更新效果
        mongoTemplate.save(book);

//        PageRequest of = PageRequest.of(1, 2);
//
//        Query query = new Query();
//        query.with(of);
//        List<Book> objects = mongoTemplate.find(query,Book.class);
//        log.info(""+objects);

//        PageResult<Object> objectPageResult = helper.pageQuery(query, Book.class, x -> x, 1,
//                4, null);
//        log.info("" + objectPageResult);
    }


}