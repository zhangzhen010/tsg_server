package com.game.data.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

/**
 * mongodb连接配置
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/13 16:55
 */
@Configuration
public class MongoConfig {

    /**
     * 默认db库
     *
     * @param dbUri
     * @return
     */
    @Bean
    @Primary
    public MongoTemplate dbMongo(@Value("${spring.data.mongodb.uri}") String dbUri) {
        return new MongoTemplate(new SimpleMongoClientDatabaseFactory(dbUri));
    }

    @Bean
    public MongoTemplate dataMongo(@Value("${spring.data.mongodb.data.uri}") String dataUri) {
        return new MongoTemplate(new SimpleMongoClientDatabaseFactory(dataUri));
    }

    @Bean
    public MongoTemplate logsMongo(@Value("${spring.data.mongodb.logs.uri}") String logsUri) {
        return new MongoTemplate(new SimpleMongoClientDatabaseFactory(logsUri));
    }

}
