package cn.fan.config;

import cn.fan.conver.BigDecimalToDecimal128Converter;
import cn.fan.conver.Decimal128ToBigDecimalConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据配置连接主库
 */
@Configuration
@ConfigurationProperties(prefix = "spring.data.mongodb.primary")
public class PrimaryMongoConfig extends AbstractMongoConfig {

    @Bean
    public MappingMongoConverter mappingMongoConverter() throws Exception {
        DefaultDbRefResolver dbRefResolver = new DefaultDbRefResolver(this.mongoDbFactory());
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, this.mongoMappingContext());
        List<Object> list = new ArrayList<>();
        list.add(new BigDecimalToDecimal128Converter());//自定义的类型转换器
        list.add(new Decimal128ToBigDecimalConverter());//自定义的类型转换器
        converter.setCustomConversions(new MongoCustomConversions(list));
        return converter;
    }



    @Bean
    public MongoMappingContext mongoMappingContext() {
        MongoMappingContext mappingContext = new MongoMappingContext();
        return mappingContext;
    }


    //默认使用第一个
    @Primary
    @Override
    @Bean(name = "primaryMongoTemplate")
    public MongoTemplate getMongoTemplate() throws Exception {
        return new MongoTemplate(this.mongoDbFactory(), this.mappingMongoConverter());
//        return new MongoTemplate(mongoDbFactory());
    }
}
