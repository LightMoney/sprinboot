package cn.fan.swaggernew.config;

import cn.fan.swaggernew.ano.ApiVersion;
import cn.fan.swaggernew.interf.ApiVersionConstant;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.ModelSpecification;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Swagger2API文档的配置
 */
@Configuration
public class Swagger2Config {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //扫描的路径包,设置basePackage会将包下的所有被@Api标记类的所有方法作为api
//                .apis(RequestHandlerSelectors.basePackage("cn.fan.swagger.controller"))
//                对@apiOperation标记的方法作为api
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .globalRequestParameters(globalRequest());//全局参数

    }

   /**
            * ApiVersion为创建的注解获取组类
 * ApiVersionConstant版本管理接口
 * @ApiVersion(group = ApiVersionConstant.FAP_APP101)就可以对应分组
 */

    //app1.0.0版本对外接口
    @Bean
    public Docket vApp100(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName(ApiVersionConstant.FAP_APP100)
                .select()
                .apis(input -> {
                    ApiVersion apiVersion = input.getHandlerMethod().getMethodAnnotation(ApiVersion.class);
                    if(apiVersion!=null&& Arrays.asList(apiVersion.group()).contains(ApiVersionConstant.FAP_APP100)){
                        return true;
                    }
                    return false;
                })//controller路径
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SwaggerUI演示")
                .description("swagger-new")
                .contact(new Contact("swagger-new", null, null))
                .version("1.0")
                .build();
    }




    private List<RequestParameter> globalRequest() {
        List<RequestParameter> pars = new ArrayList<>();
        pars.add(new RequestParameterBuilder().name("Authorization").description("token")//.required(true)
                .in(ParameterType.HEADER)
                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING))).required(false).build());
        return pars;
    }
}