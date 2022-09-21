package com.xiaoren.ruiji.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * 自定义数据对象处理器
 * 公告字段填充
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Resource
    private RedisTemplate<String, Long> redisTemplate;

    /**
     * 插入操作，自动填充
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info(metaObject.toString());
        Long userId = redisTemplate.opsForValue().get("userId");
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser", userId);
        metaObject.setValue("updateUser", userId);

    }

    /**
     * 更新操作，自动填充
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        Long userId = redisTemplate.opsForValue().get("userId");
        metaObject.setValue("updateTime", LocalDateTime.now());

        metaObject.setValue("updateUser", userId);
    }
}
