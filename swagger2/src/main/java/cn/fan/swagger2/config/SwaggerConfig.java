package cn.fan.swagger2.config;

import cn.fan.swagger2.ano.ApiVersion;
import cn.fan.swagger2.interf.ApiVersionConstant;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
    //默认版本的接口api-docs分组
    @Bean
    public Docket creatRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //扫描的路径包,设置basePackage会将包下的所有被@Api标记类的所有方法作为api
//                .apis(RequestHandlerSelectors.basePackage("cn.fan.swagger.controller"))
//                对@apiOperation标记的方法作为api
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //指定路径处理PathSelectors.any()代表所有的路径
                .paths(PathSelectors.any())
                .build()//;
.globalOperationParameters(globalOperation());//添加头信息
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

    /**
     *
     * - swagger.title=标题
     * - swagger.description=描述
     * - swagger.version=版本
     * - swagger.license=许可证
     * - swagger.licenseUrl=许可证URL
     * - swagger.termsOfServiceUrl=服务条款URL
     * - swagger.contact.name=维护人
     * - swagger.contact.url=维护人URL
     * - swagger.contact.email=维护人email
     * - swagger.base-package=swagger扫描的基础包，默认：全扫描
     * - swagger.base-path=需要处理的基础URL规则，默认：/**
     * - swagger.exclude-path=需要排除的URL规则，默认：空
     * - swagger.host=文档的host信息，默认：空
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("sprinboot中构建使用swagger2构建api")
                .description("接口说明")
                .termsOfServiceUrl("http://127.0.0.1:8080/")
                .contact("测试使用")
                .version("1.0")
                .build();
    }

    private List<Parameter> globalOperation() {
        //添加head参数配置start
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        //第一个token为传参的key，第二个token为swagger页面显示的值
        tokenPar.name("Authorization").description("token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        return pars;
    }
}
