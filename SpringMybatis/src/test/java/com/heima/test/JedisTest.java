package com.heima.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

public class JedisTest {

    private Jedis jedis;

    @BeforeEach
    void setUp(){
        jedis = new Jedis("localhost",6379);
        jedis.auth("123456");
        jedis.select(0);
    }

    @Test
    void testString(){
        String result = jedis.set("name", "test");
        System.out.println("result = :"+result);
        String name = jedis.get("name");
        System.out.println("name = :"+name);
    }

    @AfterEach
    void tearDown(){
        if(jedis!=null){
            jedis.close();
        }
    }
}
