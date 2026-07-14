package cn.xh_net.studio.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 工具类
 * @author XingHai
 * @date 2023/7/13 12:45
 * @description Redis 工具类，用于操作 Redis 缓存数据库
 */
@Component
public class RedisUtil {

    private final RedisTemplate redisTemplate;

    public RedisUtil(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 设置缓存对象
     * @param key 缓存键
     * @param value 缓存值
     */
    public <T> void setCacheObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置缓存对象
     * @param key 缓存键
     * @param value 缓存值
     * @param timeout 过期时间
     * @param timeUnit 时间单位
     */
    public <T> void setCacheObject(final String key, final T value, final Long timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 设置缓存过期时间
     * @param key 缓存键
     * @param timeout 过期时间(秒)
     * @return 是否设置成功
     */
    public boolean setExpire(final String key, final Long timeout) {
        return setExpire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置缓存过期时间
     * @param key 缓存键
     * @param timeout 过期时间
     * @param timeUnit 时间单位
     * @return 是否设置成功
     */
    public boolean setExpire(final String key, final Long timeout, final TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * 获取缓存对象
     * @param key 缓存键
     * @return 缓存值
     */
    public <T> T getCacheObject(final String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除单个缓存对象
     * @param key 缓存键
     * @return 是否删除成功
     */
    public boolean deleteCacheObject(final String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除多个缓存对象
     * @param keys 缓存键集合
     * @return 删除的键数量
     */
    public Long deleteCacheObject(final Collection<String> keys) {
        Long count = redisTemplate.delete(keys);
        count = count == null ? 0 : count;
        return count;
    }

    /**
     * 设置缓存列表
     * @param key 缓存键
     * @param values 缓存值列表
     */
    public void setCacheList(final String key, final List<?> values) {
        redisTemplate.opsForList().set(key, 0, values);
    }

    /**
     * 获取缓存列表
     * @param key 缓存键
     * @return 缓存值列表
     */
    public <T> List<T> getCacheList(final String key) {
        return (List<T>) redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 设置缓存集合
     * @param key 缓存键
     * @param values 缓存值集合
     */
    public <T> void setCacheSet(final String key, final Set<T> values) {
        redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 获取缓存集合
     * @param key 缓存键
     * @return 缓存值集合
     */
    public <T> Set<T> getCacheSet(final String key) {
        return (Set<T>) redisTemplate.opsForSet().members(key);
    }

    /**
     * @param key 缓存键
     * @param values 缓存值映射
     */
    public <T> void setCacheMap(final String key, final Map<String, T> values) {
        redisTemplate.opsForHash().putAll(key, values);
    }

    /**
     * @param key 缓存键
     * @return 缓存值映射
     */
    public Map<Object, Object> getCacheMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }
}
