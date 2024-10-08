package com.game.data.manager;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 配置提前拆分缓存manager
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/6 8:43
 */
@Component
@Log4j2
@Getter
//装载的时候会自动类名首字母小写
@DependsOn("dataManager")
public class DataCacheManager {
    /**
     * 配置库
     */
    private @Resource DataManager dataManager;

    private @Resource MongoTemplate mongoTemplate;

    @PostConstruct
    public void init() {

    }

}
