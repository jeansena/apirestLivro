package br.com.restLivro.config;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.restLivro.serialization.converter.YamlJackson2HttpMessegeConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	//cors
	@Value("${cors.originPatterns:default}")
	private String corsOriginPatterns = "";
	
	private static final MediaType MEDIA_TYPE_APPLICATION_YML= MediaType.valueOf("application/x-yaml");

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new YamlJackson2HttpMessegeConverter() );
	}

	//cors de forma global
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		var allowedOrigins = corsOriginPatterns.split(",");
		registry.addMapping("/**")
				//.allowCredentials("GET","POST","PUT") // NO CASO SE QUISER ESPECIFICAR OS VERBO DE ACESSO
				.allowedOrigins("*")
				.allowedOrigins(allowedOrigins)
				.allowCredentials(true); //- autenticação
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		
		
		configurer.favorParameter(false)
		.ignoreAcceptHeader(false)
		.useRegisteredExtensionsOnly(false)
		.defaultContentType(MediaType.APPLICATION_JSON)
			.mediaType("json", MediaType.APPLICATION_JSON)
			.mediaType("xml", MediaType.APPLICATION_XML)
			.mediaType("x-yaml", MEDIA_TYPE_APPLICATION_YML);
	}

	

}
