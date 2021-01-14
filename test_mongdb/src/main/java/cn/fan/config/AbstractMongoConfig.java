package cn.fan.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

/**
 * 抽象公共类配制类（多数据源公共类）
 */
public abstract class AbstractMongoConfig {
    //连接MongoDB地址
    private String uri;

    /**
     * 获取mongoDBTtemplate对象
     */
    public abstract MongoTemplate getMongoTemplate() throws Exception;

    /**
     * 创建mongoDb工厂
     */
    public MongoDbFactory mongoDbFactory() throws Exception {
        MongoClientURI mongoclienturi = new MongoClientURI(uri);
        return new SimpleMongoDbFactory(mongoclienturi);
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
