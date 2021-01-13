momgodb 分页的方式
        1.skip  limit
        通过skip和limit方法可以简单的实现分页操作，但是如果数据量特别巨大的时候，会出现性能的问题，建议不使用！
        2原生的分页
       //分页条件
       of页数好像是从0开始的
           //Pageable pageable = new PageRequest(pageNUmber,pageSize);
           Pageable pageable = PageRequest.of(pageNUmber,pageSize);
            PageRequest of = PageRequest.of(1, 1);
            Query query = new Query();
            query.addCriteria(Criteria.where("price").lt(40));
            query.with(of);
            List<Book> objects = mongoTemplate.find(query, Book.class);
            
配置多数据源：
1.//配置多数据源时需要排除mongo的默认配置
@SpringBootApplication(exclude={MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
  2.提取公共配置类AbstractMongoConfig,实现对应配置类
  3.若密码中含有@符号使用%40代替
  
  
  直接通过获取库名，在同一mongo中切换还没实现，上面只是替代方案      