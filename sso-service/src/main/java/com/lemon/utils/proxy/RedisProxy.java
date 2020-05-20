package com.lemon.utils.proxy;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lemon.utils.util.RedisUtils;

import redis.clients.jedis.Jedis;

/**
 * <p>Title: RedisProxy.java
 * <p>Description: Redis代理访问
 * <p>Copyright: Copyright © 2016, CQzlll, All Rights Reserved.
 *
 * @author CQzlll.zl
 * @version 1.0
 */
public class RedisProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisProxy.class);

    /**
     * Redis过期时间,以秒为单位
     */
    public final static int EXRP_MINUTE = 60;           //一分钟
    public final static int EXRP_HOUR = 60*60;          //一小时
    public final static int EXRP_DAY = 60*60*24;        //一天
    public final static int EXRP_MONTH = 60*60*24*30;   //一个月

    /**
     * 设置键值对（字符串）
     * @param key 键
     * @param value 值（字符串）
     */
    public static boolean set(String key, String value){
        StringBuilder loggerMsg = new StringBuilder();
        loggerMsg.append("RedisProxy.set(): 向Redis中设置数据");
        loggerMsg.append("(数据: ");
        loggerMsg.append("key: ");
        loggerMsg.append(key);
        loggerMsg.append(" | ");
        loggerMsg.append("value: ");
        loggerMsg.append(value);
        loggerMsg.append(")");

        boolean status = false;

        if(StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
            loggerMsg.append("被拒绝.原因为: 数据有误, 拒绝设置.");
            LOGGER.warn(loggerMsg.toString());
            return status;
        }

        Jedis jedis = null;
        try {
            jedis = RedisUtils.getJedis();
            if (jedis == null) {
                loggerMsg.append("失败.原因为: 获取Jedis失败.");
                LOGGER.warn(loggerMsg.toString());
                return status;
            }

            String statusCode = jedis.set(key, value);

            if ("OK".equals(statusCode)) {
                loggerMsg.append("成功.");
                LOGGER.info(loggerMsg.toString());
                status = true;
            } else {
                loggerMsg.append("失败.返回状态码为: ");
                loggerMsg.append(statusCode);
                loggerMsg.append(".");
                LOGGER.warn(loggerMsg.toString());
            }
        } catch(Exception ex) {
            loggerMsg.append("失败.原因: 出现异常.");
            LOGGER.error(loggerMsg.toString(), ex);
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return status;
    }

    /**
     * 设置带有过期时间的键值对（字符串）
     * @param key 键
     * @param seconds 过期时间（单位：秒）
     * @param value 值（字符串）
     */
    public static boolean setex(String key, int seconds, String value){
        StringBuilder loggerMsg = new StringBuilder();
        loggerMsg.append("RedisProxy.setex(): 向Redis中设置带过期时间的数据");
        loggerMsg.append("(数据: ");
        loggerMsg.append("key: ");
        loggerMsg.append(key);
        loggerMsg.append(" | ");
        loggerMsg.append("seconds: ");
        loggerMsg.append(seconds);
        loggerMsg.append(" | ");
        loggerMsg.append("value: ");
        loggerMsg.append(value);
        loggerMsg.append(")");

        boolean status = false;

        if(StringUtils.isBlank(key) || seconds <= 0 || StringUtils.isBlank(value)) {
            loggerMsg.append("被拒绝.原因为: 数据有误, 拒绝设置.");
            LOGGER.warn(loggerMsg.toString());
            return status;
        }

        Jedis jedis = null;
        try {
            jedis = RedisUtils.getJedis();
            if (jedis == null) {
                loggerMsg.append("失败.原因为: 获取Jedis失败.");
                LOGGER.warn(loggerMsg.toString());
                return status;
            }

            String statusCode = jedis.setex(key, seconds, value);

            if ("OK".equals(statusCode)) {
                loggerMsg.append("成功.");
                LOGGER.info(loggerMsg.toString());
                status = true;
            } else {
                loggerMsg.append("失败.返回状态码为: ");
                loggerMsg.append(statusCode);
                loggerMsg.append(".");
                LOGGER.warn(loggerMsg.toString());
            }
        } catch(Exception ex) {
            loggerMsg.append("失败.原因: 出现异常.");
            LOGGER.error(loggerMsg.toString(), ex);
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return status;
    }

    /**
     * 通过键获取值（字符串）
     * @param key 键
     * @return value 值（字符串）
     */
    public static String get(String key){
        StringBuilder loggerMsg = new StringBuilder();
        loggerMsg.append("RedisProxy.get(): 从Redis中获取数据");
        loggerMsg.append("(获取参数: ");
        loggerMsg.append("key: ");
        loggerMsg.append(key);
        loggerMsg.append(")");

        String value = null;

        if(StringUtils.isBlank(key)) {
            loggerMsg.append("被拒绝.原因为: 获取参数有误, 拒绝获取.");
            LOGGER.warn(loggerMsg.toString());
            return value;
        }

        Jedis jedis = null;
        try {
            jedis = RedisUtils.getJedis();
            if (jedis == null) {
                loggerMsg.append("失败.原因为: 获取Jedis失败.");
                LOGGER.warn(loggerMsg.toString());
                return value;
            }

            if (jedis.exists(key)) {
                value = jedis.get(key);
                loggerMsg.append("成功, 且数据为: ");
                loggerMsg.append(value);
                loggerMsg.append(".");
            } else {
                loggerMsg.append("成功, 但无数据.");
            }
            LOGGER.info(loggerMsg.toString());
        } catch (Exception ex) {
            loggerMsg.append("失败.原因: 出现异常.");
            LOGGER.error(loggerMsg.toString(), ex);
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return value;
    }

    private RedisProxy() {
        throw new RuntimeException("禁止实例化Redis请求代理类.");
    }
}
