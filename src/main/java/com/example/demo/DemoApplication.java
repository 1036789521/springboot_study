package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ComponentScan(basePackages = {"com.example"})
@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@Import({DemoApplication.Swagger2.class})
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Configuration
    @EnableSwagger2
    public class Swagger2 {
        //swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
        @Bean
        public Docket createRestApi() {
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo())
                    .select()
                    //为当前包路径
                    .apis(RequestHandlerSelectors.basePackage("com.example.demo.controller"))
                    .paths(PathSelectors.any())
                    .build();
        }
        //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
        private ApiInfo apiInfo() {
            return new ApiInfoBuilder()
                    //页面标题
                    .title("Spring Boot 测试使用 Swagger2 构建RESTful API")
                    //创建人
                    .contact("")
                    //版本号
                    .version("1.0")
                    //描述
                    .description("API 描述")
                    .build();
        }


    }

}
