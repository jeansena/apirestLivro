package br.com.restLivro.integrationtests.controller.withyaml;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import br.com.restLivro.configs.TestConfigs;
import br.com.restLivro.dataDTO.security.TokenVO;
import br.com.restLivro.integrationtests.controller.withyaml.mapper.YMLMapper;
import br.com.restLivro.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.restLivro.integrationtests.vo.AccountCredentialsVO;
import br.com.restLivro.integrationtests.vo.PessoaVO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerYamlTest extends AbstractIntegrationTest {
	
	private static RequestSpecification specification;
	private static YMLMapper objectMapper;

	private static PessoaVO person;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new YMLMapper();
		person = new PessoaVO();
	}
	
	@Test
	@Order(0)
	public void authorization() throws JsonMappingException, JsonProcessingException {
		
		AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");
		
		var accessToken = given()
				.config(
						RestAssuredConfig
							.config()
							.encoderConfig(EncoderConfig.encoderConfig()
								.encodeContentTypeAs(
									TestConfigs.CONTENT_TYPE_YML,
									ContentType.TEXT)))
				.basePath("/auth/signin")
					.port(TestConfigs.SERVER_PORT)
					.contentType(TestConfigs.CONTENT_TYPE_YML)
					.accept(TestConfigs.CONTENT_TYPE_YML)
				.body(user, objectMapper)
					.when()
				.post()
					.then()
						.statusCode(200)
							.extract()
							.body()
								.as(TokenVO.class, objectMapper)
							.getAccessToken();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		var persistedPerson = given().spec(specification)
				.config(
						RestAssuredConfig
							.config()
							.encoderConfig(EncoderConfig.encoderConfig()
								.encodeContentTypeAs(
									TestConfigs.CONTENT_TYPE_YML,
									ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.body(person, objectMapper)
					.when()
					.post()
				.then()
					.statusCode(200)
						.extract()
						.body()
							.as(PessoaVO.class, objectMapper);
		
		person = persistedPerson;
		
		assertNotNull(persistedPerson);
		
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getNome());
		assertNotNull(persistedPerson.getSegundoNome());
		assertNotNull(persistedPerson.getEndereco());
		assertNotNull(persistedPerson.getGenero());
		
		assertTrue(persistedPerson.getId() > 0);
		
		assertEquals("Nelson", persistedPerson.getNome());
		assertEquals("Piquet", persistedPerson.getSegundoNome());
		assertEquals("Brasília - DF - Brasil", persistedPerson.getEndereco());
		assertEquals("Male", persistedPerson.getGenero());
	}

	@Test
	@Order(2)
	public void testUpdate() throws JsonMappingException, JsonProcessingException {
		person.setSegundoNome("Piquet Souto Maior");
		
		var persistedPerson = given().spec(specification)
				.config(
						RestAssuredConfig
							.config()
							.encoderConfig(EncoderConfig.encoderConfig()
								.encodeContentTypeAs(
									TestConfigs.CONTENT_TYPE_YML,
									ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.body(person, objectMapper)
					.when()
					.post()
				.then()
					.statusCode(200)
						.extract()
						.body()
						.as(PessoaVO.class, objectMapper);
		
		person = persistedPerson;
		
		assertNotNull(persistedPerson);
		
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getNome());
		assertNotNull(persistedPerson.getSegundoNome());
		assertNotNull(persistedPerson.getEndereco());
		assertNotNull(persistedPerson.getGenero());
		
		assertEquals(person.getId(), persistedPerson.getId());
		
		assertEquals("Nelson", persistedPerson.getNome());
		assertEquals("Piquet Souto Maior", persistedPerson.getNome());
		assertEquals("Brasília - DF - Brasil", persistedPerson.getEndereco());
		assertEquals("Male", persistedPerson.getGenero());
	}

	@Test
	@Order(3)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		mockPerson();
			
		var persistedPerson = given().spec(specification)
				.config(
						RestAssuredConfig
							.config()
							.encoderConfig(EncoderConfig.encoderConfig()
								.encodeContentTypeAs(
									TestConfigs.CONTENT_TYPE_YML,
									ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.pathParam("id", person.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(200)
						.extract()
						.body()
						.as(PessoaVO.class, objectMapper);
		
		person = persistedPerson;
		
		assertNotNull(persistedPerson);
		
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getNome());
		assertNotNull(persistedPerson.getSegundoNome());
		assertNotNull(persistedPerson.getEndereco());
		assertNotNull(persistedPerson.getGenero());

		assertEquals(person.getId(), persistedPerson.getId());
		
		assertEquals("Nelson", persistedPerson.getNome());
		assertEquals("Piquet Souto Maior", persistedPerson.getSegundoNome());
		assertEquals("Brasília - DF - Brasil", persistedPerson.getEndereco());
		assertEquals("Male", persistedPerson.getGenero());
	}
	
	@Test
	@Order(4)
	public void testDelete() throws JsonMappingException, JsonProcessingException {

		given().spec(specification)
			.config(
				RestAssuredConfig
					.config()
					.encoderConfig(EncoderConfig.encoderConfig()
						.encodeContentTypeAs(
							TestConfigs.CONTENT_TYPE_YML,
							ContentType.TEXT)))
			.contentType(TestConfigs.CONTENT_TYPE_YML)
			.accept(TestConfigs.CONTENT_TYPE_YML)
				.pathParam("id", person.getId())
				.when()
				.delete("{id}")
			.then()
				.statusCode(204);
	}
	
	@Test
	@Order(5)
	public void testFindAll() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.config(
						RestAssuredConfig
							.config()
							.encoderConfig(EncoderConfig.encoderConfig()
								.encodeContentTypeAs(
									TestConfigs.CONTENT_TYPE_YML,
									ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.when()
					.get()
				.then()
					.statusCode(200)
						.extract()
						.body()
						.as(PessoaVO[].class, objectMapper);
		
		List<PessoaVO> people = Arrays.asList(content);

		PessoaVO foundPersonOne = people.get(0);
		
		assertNotNull(foundPersonOne.getId());
		assertNotNull(foundPersonOne.getNome());
		assertNotNull(foundPersonOne.getSegundoNome());
		assertNotNull(foundPersonOne.getEndereco());
		assertNotNull(foundPersonOne.getGenero());
		
		assertEquals(1, foundPersonOne.getId());
		
		assertEquals("Ayrton", foundPersonOne.getNome());
		assertEquals("Senna", foundPersonOne.getSegundoNome());
		assertEquals("São Paulo", foundPersonOne.getEndereco());
		assertEquals("Male", foundPersonOne.getGenero());

		PessoaVO foundPersonSix = people.get(5);



		
		assertNotNull(foundPersonSix.getId());
		assertNotNull(foundPersonSix.getNome());
		assertNotNull(foundPersonSix.getSegundoNome());
		assertNotNull(foundPersonSix.getId());
		assertNotNull(foundPersonSix.getGenero());
		
		assertEquals(9, foundPersonSix.getId());
		
		assertEquals("Nelson", foundPersonSix.getNome());
		assertEquals("Mvezo", foundPersonSix.getSegundoNome());
		assertEquals("Mvezo – South Africa", foundPersonSix.getEndereco());
		assertEquals("Male", foundPersonSix.getGenero());
	}

	
	@Test
	@Order(6)
	public void testFindAllWithoutToken() throws JsonMappingException, JsonProcessingException {
		
		RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
			.setBasePath("/api/person/v1")
			.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
			.build();
		
		given().spec(specificationWithoutToken)
			.config(
				RestAssuredConfig
					.config()
					.encoderConfig(EncoderConfig.encoderConfig()
						.encodeContentTypeAs(
							TestConfigs.CONTENT_TYPE_YML,
							ContentType.TEXT)))
			.contentType(TestConfigs.CONTENT_TYPE_YML)
			.accept(TestConfigs.CONTENT_TYPE_YML)
				.when()
				.get()
			.then()
				.statusCode(403);
	}
	
	private void mockPerson() {
		person.setNome("Nelson");
		person.setSegundoNome("Piquet");
		person.setEndereco("Brasília - DF - Brasil");
		person.setGenero("Male");
	}
}