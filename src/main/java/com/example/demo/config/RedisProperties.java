package com.example.demo.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
* @Description: redis配置项
*
* @Author wangyanlin
* @Date 14:38 2019/3/11
* @Param
* @return
**/
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {

    /**
    * @Description: 地址
    *
    * @Author wangyanlin
    * @Date 14:35 2019/3/11
    * @Param
    * @return
    **/
    private String host;

    /**
    * @Description: 密码
    *
    * @Author wangyanlin
    * @Date 14:36 2019/3/11
    * @Param
    * @return
    **/
    private String password;

    /**
    * @Description: 端口
    *
    * @Author wangyanlin
    * @Date 14:37 2019/3/11
    * @Param
    * @return
    **/
    private Integer port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
