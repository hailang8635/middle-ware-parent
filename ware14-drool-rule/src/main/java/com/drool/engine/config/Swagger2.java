package com.drool.engine.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// TODO
@Profile("dev")
@Configuration
@EnableSwagger2
public class Swagger2 {

    /** swagger start ///////////////////////////////////////////////////////////////////////////////////
     *
     */
    @Value("${swagger.enable:true}")
    private boolean enableSwagger;

    @Bean
    public Docket api() {
        //        return new Docket(DocumentationType.SWAGGER_2)
        // .select()
        // .paths(PathSelectors.regex("^/api/.*$")).build();

        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enableSwagger)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.drool.engine"))
                .paths(PathSelectors.any())
                .build();
    }
    // swagger end ///////////////////////////////////////////////////////////////////////////////////

}
