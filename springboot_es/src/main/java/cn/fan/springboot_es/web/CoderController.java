package cn.fan.springboot_es.web;

import cn.fan.springboot_es.dao.CoderEsRepository;
import cn.fan.springboot_es.entity.Coder;
import cn.fan.springboot_es.entity.SubCoder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;


/**
 * matchQuery：词条匹配，先分词然后在调用termQuery进行匹配
 *
 * TermQuery：词条匹配，不分词
 *
 * wildcardQuery：通配符匹配
 *
 * fuzzyQuery：模糊匹配
 *
 * rangeQuery：范围匹配
 *
 * booleanQuery：布尔查询
 */
@RestController
@RequestMapping("/persons/coder")
public class CoderController {

    @Autowired
    CoderEsRepository coderEsRepository;


//    @Autowired
//    private ElasticsearchTemplate template;
    /**
     * @return
     */
    @GetMapping
    public ResponseEntity get() {
        //单个属性查询
//        List<Coder> coderByName= coderEsRepository.findByName("lf666'");
        //单个属性分页查询
//        Page<Coder> coderByPaging = coderEsRepository.findByCodeName("lf666", PageRequest.of(0, 10));

        List<Coder> byAgeDesc = coderEsRepository.findByCodeNameIsLikeOrderByAgeDesc("l");


        //属性是对象时查询
        SubCoder subCoder = new SubCoder();
        subCoder.setAge(20);
        subCoder.setName("subEden");
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("subCoder.name", subCoder.getName()))
                .must(QueryBuilders.matchQuery("subCoder.age", subCoder.getAge()));
        Iterable<Coder> search = coderEsRepository.search(boolQueryBuilder);

        //属性集合中是否包含指定值查询
        BoolQueryBuilder zsan = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("address", "bj"));
        Iterable<Coder> search1 = coderEsRepository.search(zsan);

        //复杂查询自定义排序规则

        BoolQueryBuilder builder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery("age").from(10).to(200));

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withFilter(builder)
                .withSort(SortBuilders.fieldSort("age").order(SortOrder.ASC));
        Iterable<Coder> coders = coderEsRepository.search(nativeSearchQueryBuilder.build());
        BoolQueryBuilder builder1 = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery("age").from(10).to(200));
        return new ResponseEntity(coders, HttpStatus.OK);
    }

    @PutMapping("/add")
    public void addIndex(){
        Coder coder=new Coder();
        coder.setCodeName("治理测试");
        coder.setAge(9);
        SubCoder subCoder=new SubCoder();
        subCoder.setAge(15);
        subCoder.setName("说我");
        coder.setSubCoder(subCoder);
//        es没有修改，实质是先删除再添加
//        coderEsRepository.save(coder);
        coderEsRepository.index(coder);
        //批量添加
//        List<Coder> list = new LinkedList<>();
//        list.add(new Coder());
//        list.add(new Coder());
//        list.add(new Coder());
//        coderEsRepository.saveAll(list);
    }
    @GetMapping("/search")
    public Iterable<Coder>getBy(){
//        List<Coder> byAgeDesc = coderEsRepository.findByNameIsLikeOrderByAgeDesc("co");
//        return byAgeDesc;
//        return coderEsRepository.findByName("co");
        BoolQueryBuilder zsan = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("codeName", "治"));
        Iterable<Coder> search1 = coderEsRepository.search(zsan);
        return search1;
    }
    @DeleteMapping("/delete")
    public void delete(){
        coderEsRepository.deleteAll();
    }
}