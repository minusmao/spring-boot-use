package com.example.spring.boot.use.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * swagger配置类
 *
 * @author minus
 * @since 2022/11/19 21:58
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {
    @Bean
    public Docket docketGroup1() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .groupName("分组名称1")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.spring.boot.use.swagger.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket docketGroup2() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .groupName("分组名称2")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.spring.boot.use.swagger.controller"))
                .paths(PathSelectors.ant("/api/use/swagger/test/get3/**"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("项目标题")
                .description("项目描述")
                .contact(new Contact("minus", "", "minus@gmail.com"))
                .version("1.0")
                .build();
    }
}
