package cn.fan.service;

import cn.fan.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.junit.internal.requests.ClassRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Auth Mr.fan
 * Date 2020/1/14 9:54
 **/
@Service
@Slf4j
public class MongoDbService {
    @Autowired
    private MongoTemplate mongoTemplate;
    /**
     * 保存对象
     * @param book
     * @return
     */
    public String saveObj(Book book) {
        book.setCreateTime(new Date());
        book.setUpdateTime(new Date());
        mongoTemplate.save(book);
        return "添加成功";
    }

    /**
     * 查询所有
     * @return
     */
    public List<Book> findAll() {
        return mongoTemplate.findAll(Book.class);
    }

    /***
     * 根据id查询
     * @param id
     * @return
     */
    public Book getBookById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, Book.class);
    }

    /**
     * 根据名称查询
     *
     * @param name
     * @return
     */
    public Book getBookByName(String name) {
        Query query = new Query(Criteria.where("name").is(name));
        return mongoTemplate.findOne(query, Book.class);
    }

    /**
     * 多条件查询
     * @return
     */
    public List<Book> manyCase(){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(20));
        query.addCriteria(Criteria.where("price").gte(1200));//大于等于
        return mongoTemplate.find(query,Book.class);

    }


    //查询多条数据: 属于分级查询,limit不指定就查所有，skip表示跳过的行数，不指定就不跳过
    //mongoTemplate.find(new Query().limit(rows).skip((page-1)*rows),Emps.class)
    //pageindex显示的当前页，pageSize，显示的记录数
    public PageImpl<Book> getPagedUser(int page, int rows) {
        System.out.println("查询多条数据: 属于分级查询");
        Query query=new Query();
        //每页五个
        Pageable pageable=new PageRequest(page,rows);
        query.with(pageable);
        //按sal排序
        query.with(new Sort(Sort.Direction.DESC,"price"));
        //查询总数
        Long count=mongoTemplate.count(query,Book.class,"book");
        List<Book> emps=mongoTemplate.find(query,Book.class);
        return (PageImpl<Book>) PageableExecutionUtils.getPage(emps,pageable,()->count);
    }




    public List<ClassRequest> calculate() {
        //Aggregation.match为条件筛选操作
        //Aggregation.group为分组操作
        //Aggregation.project为选择结果字段操作
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(Criteria.where("college").is("计算机学院")),
                Aggregation.group("className","college").count().as("count")
                        .max("age").as("max")
                        .min("age").as("min")
                ,Aggregation.project().and("className").as("name").andInclude("college","count","max","min")
        );
        AggregationResults<ClassRequest> aggregationResults =
                mongoTemplate.aggregate(aggregation, Book.class, ClassRequest.class);

        return aggregationResults.getMappedResults();
    }

    /**
     * 更新对象
     *
     * @param book
     * @return
     */
    public String updateBook(Book book) {
        Query query = new Query(Criteria.where("_id").is(book.getId()));
        Update update = new Update().set("publish", book.getPublish()).set("info", book.getInfo()).set("updateTime",
                new Date());
        // updateFirst 更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query, update, Book.class);
        // updateMulti 更新查询返回结果集的全部
        // mongoTemplate.updateMulti(query,update,Book.class);
        // upsert 更新对象不存在则去添加
        // mongoTemplate.upsert(query,update,Book.class);

        return "success";
    }

    /***
     * 删除对象
     * @param book
     * @return
     */
    public String deleteBook(Book book) {
        mongoTemplate.remove(book);

        return "success";
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    public String deleteBookById(String id) {
        // findOne
        Book book = getBookById(id);
        // delete
        deleteBook(book);
        return "success";
    }

    /**
     * 模糊查询
     * @param search
     * @return
     */
    public List<Book> findByLikes(String search){
        Query query = new Query();
       // Criteria criteria = new Criteria();
        //criteria.where("name").regex(search);
        Pattern pattern = Pattern.compile("^.*" + search + ".*$" , Pattern.CASE_INSENSITIVE);
        Criteria.where("name").regex(pattern);
        List<Book> lists = mongoTemplate.findAllAndRemove(query, Book.class);
        return lists;
    }

}
