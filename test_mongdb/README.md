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
  
 关于decimal与bigdecimal的接收转换
 低版本，使用下的配置即可
 @Configuration
 public class MongodbConfig{
 
     @Bean
     public CustomConversions customConversions() {
         List<Converter<?, ?>> converterList = new ArrayList<Converter<?, ?>>();
         converterList.add(new BigDecimalToDecimal128Converter());
         converterList.add(new Decimal128ToBigDecimalConverter());
         return new CustomConversions(converterList);
     }
 
 } 
 高版本见该项目配置类  CustomConversions高版本中过期
  
  分析查询方式，类似使用client的分析方式，分为以下几个步骤实现：
  
   步骤一：实现 （state1=11 and state2=22）
  
   query.addCriteria(
  
    new Criteria().andOperator(
  
    Criteria.where("state1").is(11),
  
    Criteria.where("state2").is(22)
  
    )
  
    );
  
   步骤二：使用or形式实现 value >300
  
   query.addCriteria(
  
    new Criteria().orOperator(
  
    Criteria.where("value").gte(300)
  
    )
  
    );
  
   步骤三：将步骤一参数拼接到步骤二or条件
  
   query.addCriteria(
  
    new Criteria().orOperator(
  
    Criteria.where("value").gte(300),
  
    new Criteria().andOperator(
  
    Criteria.where("state1").is(11),
  
    Criteria.where("state2").is(22)
  
    )
  
    )
  
   );
  
   
  
  
   升级查询，实际场景中要根据传输的参数是否为空，拼接查询条件：
  
   (1)如果最外层是and关系(
   query.add多个creterria默认为and关系)
  
   if(条件){
  
    query.addCriteria(Criteria.where);
  
   }
  
   if(条件){
  
    query.addCriteria(Criteria.where);
  
   }
  
   if(条件){
  
    query.addCriteria(Criteria.where);
  
   }
  
   默认拼接的query条件为and形式。
  
   (1)如果最外层是or关系
   (目前只想到此笨方法)
  
   //1.拼接参数
  
    Criteria operator1=null;
  
    Criteria operator2=null;
  
    if(1==1){//模拟判断条件
  
    operator1 = new Criteria().andOperator(
  
    Criteria.where("state1").is(11),
  
    Criteria.where("state2").is(22)
  
    );
  
    }
  
    if(1==1){//模拟判断条件
  
    operator2 = Criteria.where("value").gte(300);
  
    }
  
   //2.判断参数
  
    if(operator1!=null && operator2!=null){
  
    query.addCriteria(new Criteria().orOperator(operator1,operator2));
  
    }else if(operator1!=null){
  
    query.addCriteria(operator1);
  
    }else if(operator2!=null){
  
    query.addCriteria(operator2);
  
    }   