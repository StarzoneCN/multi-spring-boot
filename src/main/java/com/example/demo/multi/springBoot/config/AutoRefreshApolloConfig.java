package com.example.demo.multi.springBoot.config;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.enums.PropertyChangeType;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * 自动更新阿波罗配置
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/8/18 10:11
 * @modefied:
 */
@Configuration
public class AutoRefreshApolloConfig {
    private static final String REDIS_CONFIG_PREFIX = "redis";
    private static final String HOST_CONFIG_STR= "host";
    @Autowired
    private RedisConfig redisConfig;

    public AutoRefreshApolloConfig(){
        super();
        Config config = ConfigService.getAppConfig();
        config.addChangeListener(new ConfigChangeListener() {
            @Override
            public void onChange(ConfigChangeEvent changeEvent) {
                for (String key : changeEvent.changedKeys()) {
                    ConfigChange change = changeEvent.getChange(key);
                    System.out.println(String.format(
                            "Found change - key: %s, oldValue: %s, newValue: %s, changeType: %s",
                            change.getPropertyName(), change.getOldValue(),
                            change.getNewValue(), change.getChangeType()));
                    if (change.getPropertyName().startsWith(REDIS_CONFIG_PREFIX)/* && change.getChangeType() != PropertyChangeType.DELETED*/){
                        if (change.getPropertyName().equals(REDIS_CONFIG_PREFIX + "." + HOST_CONFIG_STR)) {
                            redisConfig.setHost(change.getNewValue());
                        }
                    }
                }
            }
        });
    }
}
