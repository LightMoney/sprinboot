package cn.fan.springboot_es.dao;

import cn.fan.springboot_es.entity.Coder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface CoderEsRepository extends ElasticsearchRepository<Coder, Long> {


    /**
     * 相当于ES查询语句
     * <code>
     *
     *     { "bool" :
     *       { "must" : [
     *             { "field" : {"name" : "?"} },
     *             { "field" : {"age" : "?"} }
     *         ]
     *     }
     * }
     * </code>
     * @param name
     * @param age
     * @return
     */

    List<Coder> findByNameAndAge(String name, Integer age);

    /**
     * 相当于ES查询语句
     * <code>
     *
     *
     * {"bool" : {"should" : [ {"field" : {"name" : "?"}}, {"field" : {"age" : "?"}} ]}}
     *
     * </code>
     * @param name
     * @param age
     * @return
     */

    List<Coder> findByNameOrAge(String name, Integer age);


    /**
     * {"bool" : {"must" : {"field" : {"name" : "?"}}}}
     * @param name
     * @return
     */
    List<Coder> findByName(String name);

    /**
     * {"bool" : {"must_not" : {"field" : {"name" : "?"}}}}
     * @param name
     * @return
     */
    List<Coder> findByNameNot(String name);

 /**
     * {"bool" : {"must" : {"range" : {"age" : {"from" : ?,"to" : ?,"include_lower" : true,"include_upper" : true}}}}}
     * @param fromAge
     * @param toAge
     * @return
     */
    List<Coder> findByAgeBetween(Integer fromAge,Integer toAge);

    /**
     *
     *  {"bool" : {"must" : {"range" : {"age" : {"from" : null,"to" : ?,"include_lower" : true,"include_upper" : true}}}}}
     * @param age
     * @return
     */
    List<Coder> findByAgeLessThanEqual(Integer age);


    /**
     * {"sort" : [{ "age" : {"order" : "desc"} }],"bool" : {"must" : {"field" : {"name" : ?}}}}
     * @param name
     * @return
     */
    List<Coder> findByNameIsLikeOrderByAgeDesc(String name);


    /**
     * Query 注解查询方式
     * @param name
     * @param pageable
     * @return
     */
    @Query("{\"bool\" : {\"must\" : {\"term\" : {\"name\" : \"?0\"}}}}")
    Page<Coder> findByName(String name, Pageable pageable);

}