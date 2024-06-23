package br.com.restLivro.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;

// configuração do swagger ui
// http://localhost:8080/swagger-ui/index.html  na web
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI custoOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Api restfull java 21 cadastro de livros")
                        .version("v1")
                        .description("cadastro de livros")
                        .termsOfService("https//")
                        .license( new License()
                                .name("Apache 2.0")
                                .url("https://lins da pagina na web")
                        )
                );
    }
}
