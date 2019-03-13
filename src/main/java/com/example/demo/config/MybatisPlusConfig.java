package com.example.demo.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
* @Description: mybatis-plus配置
*
* @Author wangyanlin
* @Date 14:16 2019/3/13
* @Param
* @return
**/
@Configuration
@MapperScan("com.example.demo.mapper")
public class MybatisPlusConfig {

/**
* @Description: 分页插件
*
* @Author wangyanlin
* @Date 14:17 2019/3/13
* @Param []
* @return com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor
**/
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
    * @Description: 开始逻辑删除
    *
    * @Author wangyanlin
    * @Date 14:17 2019/3/13
    * @Param []
    * @return com.baomidou.mybatisplus.core.injector.ISqlInjector
    **/
    @Bean
    public ISqlInjector sqlInjector(){
        return new LogicSqlInjector();
    }

   /**
   * @Description: 打印sql
   *
   * @Author wangyanlin
   * @Date 14:17 2019/3/13
   * @Param []
   * @return com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor
   **/
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        // 格式化sql语句
        Properties properties = new Properties();
        properties.setProperty("format", "true");
        performanceInterceptor.setProperties(properties);

        return performanceInterceptor;
    }
}