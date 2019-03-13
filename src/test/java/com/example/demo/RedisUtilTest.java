package com.example.demo;

import com.example.demo.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class RedisUtilTest {
    @Autowired
    private RedisUtil redisUtil;
    @Test
    public  void testGet(){
        redisUtil.set("testwyl","123");
        Object o = redisUtil.get("testwyl");
        System.out.println(o.toString());
        redisUtil.remove("testwyl");
    }
}
