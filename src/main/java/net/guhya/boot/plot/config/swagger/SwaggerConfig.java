package net.guhya.boot.plot.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import net.guhya.boot.plot.common.web.request.Box;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@PropertySource({ 
	  "classpath:/application${env:}.properties"
})
public class SwaggerConfig {
	
	private final static String BASE_PACKAGE = "net.guhya.boot.plot";
	
	@Bean
    public Docket api() {
		
        return new Docket(DocumentationType.SWAGGER_2)
        	.ignoredParameterTypes(Box.class)
        	.select()
            .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
            .paths(PathSelectors.ant("/v1/**"))
            .build()
            .apiInfo(apiEndPointsInfo());
    }

    private ApiInfo apiEndPointsInfo() {

        return new ApiInfoBuilder().title("Spring Boot REST API")
            .description("Spring Boot, Hashmap based dto, MyBatis, "
            		+ "Derby Embedded Database, and Map plotting")
            .license("Apache 2.0")
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
            .version("1.0.0")
            .build();
    }	
}
