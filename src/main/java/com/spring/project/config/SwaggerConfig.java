package com.spring.project.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI swaggerCustomConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("Blog app spring practice project")
                        .description("By Divyesh"))
                .tags(Arrays.asList(
                        new Tag().name("Public APIs"),
                        new Tag().name("Admin APIs"),
                        new Tag().name("User APIs"),
                        new Tag().name("Journal APIs")
                ));
    }
}
