package com.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
* @Description: redis工具类
*
* @Author wangyanlin
* @Date 11:51 2019/3/13
* @Param
* @return
**/
@Component
public class RedisUtil {

    private RedisTemplate<Serializable, Object> redisTemplate;


    @Autowired
    public void setRedisTemplate(RedisTemplate<Serializable, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 删除对应的value
     *
     * @param redisKey
     */
    public void remove(final String redisKey) {
        if (exists(redisKey)) {
            redisTemplate.delete(redisKey);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param redisKey
     * @return
     */
    public boolean exists(final String redisKey) {
        return redisTemplate.hasKey((redisKey));
    }


    /**
     * 读取缓存
     *
     * @param redisKey
     * @return
     */
    public Object get(final String redisKey) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(redisKey);
        return result;
    }

    /**
     * 写入缓存
     *
     * @param redisKey
     * @param value
     * @return
     */
    public boolean set(final String redisKey, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(redisKey, value);
            result = true;
        } catch (Exception e) {
           // logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 设置过期时间
     *
     * @param redisKey
     * @param time
     * @return
     */
    public boolean expire(final String redisKey, Long time) {
        boolean result = false;
        try {
            result = redisTemplate.expire(redisKey, time, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            //logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 设置过期时间
     *
     * @param redisKey
     * @param timeUnit
     * @return
     */
    public Long getExpire(final String redisKey, TimeUnit timeUnit) {
        Long result = null;
        try {
            result = redisTemplate.getExpire(redisKey, timeUnit);
        } catch (Exception e) {
            e.printStackTrace();
           // logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param redisKey
     * @param value
     * @return
     */
    public boolean set(final String redisKey, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(redisKey, value);
            redisTemplate.expire(redisKey, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
         //   logger.error(e.getMessage(), e);
        }
        return result;
    }


    /**
     * 设置新值，同时返回旧值
     *
     * @param redisKey
     * @param value
     * @return
     */
    public String getSet(final String redisKey, final String value) {
        String result = redisTemplate.execute((RedisCallback<String>) redisConnection -> {
            byte[] bytes = redisConnection.getSet(redisKey.getBytes(), value.getBytes());
            if (bytes != null) {
                return new String(bytes);
            }
            return null;
        });
        return result;
    }

    /**
     * 如果不存在key则插入
     *
     * @param redisKey
     * @param stringOfLockExpireTime
     * @return true 插入成功， false 插入失败
     */
    public boolean setnx(final String redisKey, final String stringOfLockExpireTime) {
        return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> redisConnection.setNX((redisKey).getBytes(), stringOfLockExpireTime.getBytes()));
    }

    /**
     * 自增
     *
     * @param redisKey
     * @return
     */
    public Long incr(final String redisKey) {
        return redisTemplate.execute((RedisCallback<Long>) redisConnection -> redisConnection.incr((redisKey).getBytes()));
    }


    /**
     * setnx 和 getSet方式插入的数据，调用此方法获取
     *
     * @param redisKey
     * @return
     */
    public String getInExecute(final String redisKey) {
        String result = redisTemplate.execute((RedisCallback<String>) redisConnection -> {
            byte[] bytes = redisConnection.get((redisKey).getBytes());
            if (bytes == null) {
                return null;
            } else {
                return new String(bytes);
            }
        });
        return result;
    }


    /**
     * 将缓存保存在map集合中
     *
     * @param redisKey
     * @param mapKey
     * @param mapValue
     * @return
     */
    public boolean putInMap(final String redisKey, String mapKey, Object mapValue) {
        boolean result = false;
        try {
            HashOperations<Serializable, Object, Object> operations = redisTemplate.opsForHash();
            operations.put((redisKey), mapKey, mapValue);
            result = true;
        } catch (Exception e) {
         //   logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 将缓存保存在map集合中
     *
     * @param redisKey
     * @param map
     * @return
     */
    public boolean putInMap(final String redisKey, Map map) {
        boolean result = false;
        try {
            HashOperations<Serializable, Object, Object> operations = redisTemplate.opsForHash();
            operations.putAll((redisKey), map);
            result = true;
        } catch (Exception e) {
            //logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 通过key从Map集合中获取value
     *
     * @param redisKey
     * @param mapKey
     * @return
     */
    public Object getOneByMapKey(final String redisKey, String mapKey) {
        HashOperations<Serializable, Object, Object> operations = redisTemplate.opsForHash();
        return operations.get((redisKey), mapKey);
    }

    /**
     * 通过多个key从Map集合获取多个value
     *
     * @param redisKey
     * @return
     */
    public Object getMultiByMapKeys(final String redisKey, Collection mapKeys) {
        HashOperations<Serializable, Object, Object> operations = redisTemplate.opsForHash();
        return operations.multiGet((redisKey), mapKeys);
    }

    /**
     * 获取Map集合的所有value
     *
     * @param redisKey
     * @return
     */
    public Object getAllFromMap(final String redisKey) {
        HashOperations<Serializable, Object, Object> operations = redisTemplate.opsForHash();
        return operations.values((redisKey));
    }

    /**
     * 获取Map集合的所有值
     *
     * @param redisKey
     * @return
     */
    public Map getEntriesFromMap(final String redisKey) {
        HashOperations<Serializable, Object, Object> operations = redisTemplate.opsForHash();
        return operations.entries((redisKey));
    }


    /**
     * 删除Map中的一个键值对
     *
     * @param redisKey
     * @param mapKey
     */
    public void removeOneFromMap(final String redisKey, Object mapKey) {
        HashOperations<Serializable, Object, Object> operations = redisTemplate.opsForHash();
        operations.delete((redisKey), mapKey);
    }

    /**
     * 删除Map中的多个键值对
     *
     * @param redisKey
     * @param mapKeys
     */
    public void removeMultiFromMap(final String redisKey, Object... mapKeys) {
        HashOperations<Serializable, Object, Object> operations = redisTemplate.opsForHash();
        operations.delete((redisKey), mapKeys);
    }

    /**
     * 添加一条记录到List集合
     *
     * @param redisKey
     * @param value
     * @return
     */
    public boolean addInList(final String redisKey, Object value) {
        boolean result = false;
        try {
            ListOperations<Serializable, Object> listOperations = redisTemplate.opsForList();
            listOperations.leftPush((redisKey), value);
            result = true;
        } catch (Exception e) {
         //   logger.error(e.getMessage(), e);
        }
        return result;
    }

    public boolean addInListOfRight(final String redisKey, Object value) {
        boolean result = false;
        try {
            ListOperations<Serializable, Object> listOperations = redisTemplate.opsForList();
            listOperations.rightPush((redisKey), value);
            result = true;
        } catch (Exception e) {
           // logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 得到List集合所有记录
     *
     * @param redisKey
     * @return
     */
    public Object getList(final String redisKey) {
        ListOperations<Serializable, Object> listOperations = redisTemplate.opsForList();
        return listOperations.range((redisKey), 0, listOperations.size((redisKey)));
    }

    public Object rightPop(final String redisKey) {
        ListOperations<Serializable, Object> listOperations = redisTemplate.opsForList();
        return listOperations.rightPop((redisKey));
    }

/*    *//**
     * 注意，每个微服务初始化时固定绑定一个DB，不要切换DB。
     * 因为redisTemplate是静态的切换DB可能会错误先后覆盖
     * @param dbIndex
     *//*
    private void changeDB(int dbIndex) {
        LettuceConnectionFactory jedisConnectionFactory = (LettuceConnectionFactory) redisTemplate.getConnectionFactory();
        jedisConnectionFactory.setDatabase(dbIndex);
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        jedisConnectionFactory.resetConnection();
    }*/

}
