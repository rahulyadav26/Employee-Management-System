package com.assignment.application.service;

import com.assignment.application.Constants.StringConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class RedisService {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    public String getKeyValue(String regex){
        Jedis jedis = jedisPool.getResource();
        String token = jedis.get(regex);
        return token;
    }

    public String deleteKey(String regex){
        Jedis jedis = jedisPool.getResource();
        jedis.del(regex);
        return StringConstant.DELETION_SUCCESSFUL;
    }

}
