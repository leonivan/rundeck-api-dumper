package org.rundeck.api.config

import com.google.common.base.Predicate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

import static com.google.common.base.Predicates.or
import static springfox.documentation.builders.PathSelectors.regex

@Configuration
@EnableSwagger2
class Swagger {

    @Bean
    def Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("public-api")
                .apiInfo(apiInfo()).select().paths(postPaths()).build();
    }

    def Predicate<String> postPaths() {
        //return or(regex("/api/project.*"), regex("/api/projects.*"), regex("/api/funciona.*"))
        return or(regex("/*.*"))
    }

    def ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Rundeck Dumper API")
                .description("Rundeck dumper API reference")
                .termsOfServiceUrl("http://jalasoft.com")
                .contact("richyyjd@gmail.com").license("Apache License")
                .licenseUrl("richyyjd@gmail.com").version("1.0").build()
    }
}
