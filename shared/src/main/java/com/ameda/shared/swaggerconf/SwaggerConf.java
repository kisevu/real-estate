package com.ameda.shared.swaggerconf;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: kev.Ameda
 */

@Configuration
public class SwaggerConf {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Service")
                        .version("1.0")
                        .description("API Kevin Ameda Kisevu documentation"));
    }
}
