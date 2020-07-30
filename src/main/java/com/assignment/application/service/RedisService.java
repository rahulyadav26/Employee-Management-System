package com.assignment.application.service;

import com.assignment.application.constants.StringConstant;
import com.assignment.application.exception.NotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

    public String findAndDelete(String regex,String companyId){
        Set<String> keySet = getKeys(regex);
        if(keySet.isEmpty()){
            throw new NotExistsException("No employee token found");
        }
        Iterator<String> iterator = keySet.iterator();
        int track=0;
        while (iterator.hasNext()){
            String token = iterator.next();
            String[] tokenSplit = token.split("::");
            String[] fetchEmpToken = tokenSplit[1].split("-");
            if(fetchEmpToken[fetchEmpToken.length-1].equals(companyId)){
                deleteKey(token);
                track++;
                break;
            }
        }
        if(track==0){
            throw new NotExistsException("No such employee exists");
        }
        return StringConstant.DELETION_SUCCESSFUL;
    }

    public Set<String> getKeys(String regex){
        Jedis jedis = jedisPool.getResource();
        Set<String> keySet = new HashSet<>();
        ScanParams scanParams = new ScanParams();
        scanParams.match(regex+"*");
        String nextIndex = "0";
        do {
            ScanResult<String> scanResult = jedis.scan(nextIndex, scanParams);
            List<String> keys = scanResult.getResult();
            nextIndex = scanResult.getCursor();
            keySet.addAll(keys);

        } while (!nextIndex.equals("0"));

        return keySet;

    }
}
