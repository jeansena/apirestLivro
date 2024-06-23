package br.com.restLivro.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import br.com.restLivro.Service.PessoaService;
import br.com.restLivro.dataDTO.PessoaVO;
import br.com.restLivro.exceptions.necessarioexcecaonula;
import br.com.restLivro.repository.PessoaRepository;
import br.com.restLivro.unittests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import br.com.restLivro.model.pessoa;



@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

	MockPerson input;
	
	@InjectMocks
	private PessoaService service;
	
	@Mock
	PessoaRepository repository;
	
	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockPerson();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() {
		pessoa entity = input.mockEntity(1);
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		var result = service.findById(1L);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		
		assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		assertEquals("Addres Test1", result.getEndereco());
		assertEquals("First Name Test1", result.getNome());
		assertEquals("Last Name Test1", result.getSegundoNome());
		assertEquals("Female", result.getGenero());
	}
	
	@Test
	void testCreate() {
		pessoa entity = input.mockEntity(1);
		entity.setId(1L);

		pessoa persisted = entity;
		persisted.setId(1L);
		
		PessoaVO vo = input.mockVO(1);
		vo.setKey(1L);
		
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.create(vo);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		
		assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		assertEquals("Addres Test1", result.getEndereco());
		assertEquals("First Name Test1", result.getNome());
		assertEquals("Last Name Test1", result.getSegundoNome());
		assertEquals("Female", result.getGenero());
	}
	
	@Test
	void testCreateWithNullPerson() {
		Exception exception = assertThrows(necessarioexcecaonula.class, () -> {
			service.create(null);
		});
		
		String expectedMessage = "It is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}


	@Test
	void testUpdate() {
		pessoa entity = input.mockEntity(1);
		
		pessoa persisted = entity;
		persisted.setId(1L);
		
		PessoaVO vo = input.mockVO(1);
		vo.setKey(1L);
		

		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.update(vo);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		
		assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		assertEquals("Addres Test1", result.getEndereco());
		assertEquals("First Name Test1", result.getNome());
		assertEquals("Last Name Test1", result.getSegundoNome());
		assertEquals("Female", result.getGenero());
	}
	

	
	@Test
	void testUpdateWithNullPerson() {
		Exception exception = assertThrows(necessarioexcecaonula.class, () -> {
			service.update(null);
		});
		
		String expectedMessage = "It is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void testDelete() {
		pessoa entity = input.mockEntity(1);
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		service.delete(1L);
	}
	
	@Test
	void testFindAll() {
		List<pessoa> list = input.mockEntityList();
		
		when(repository.findAll()).thenReturn(list);
		
		var people = service.findAll();
		
		assertNotNull(people);
		assertEquals(14, people.size());
		
		var personOne = people.get(1);
		
		assertNotNull(personOne);
		assertNotNull(personOne.getKey());
		assertNotNull(personOne.getLinks());
		
		assertTrue(personOne.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		assertEquals("Addres Test1", personOne.getEndereco());
		assertEquals("First Name Test1", personOne.getNome());
		assertEquals("Last Name Test1", personOne.getSegundoNome());
		assertEquals("Female", personOne.getGenero());
		
		var personFour = people.get(4);
		
		assertNotNull(personFour);
		assertNotNull(personFour.getKey());
		assertNotNull(personFour.getLinks());
		
		assertTrue(personFour.toString().contains("links: [</api/person/v1/4>;rel=\"self\"]"));
		assertEquals("Addres Test4", personFour.getEndereco());
		assertEquals("First Name Test4", personFour.getNome());
		assertEquals("Last Name Test4", personFour.getSegundoNome());
		assertEquals("Male", personFour.getGenero());
		
		var personSeven = people.get(7);
		
		assertNotNull(personSeven);
		assertNotNull(personSeven.getKey());
		assertNotNull(personSeven.getLinks());
		
		assertTrue(personSeven.toString().contains("links: [</api/person/v1/7>;rel=\"self\"]"));
		assertEquals("Addres Test7", personSeven.getEndereco());
		assertEquals("First Name Test7", personSeven.getNome());
		assertEquals("Last Name Test7", personSeven.getSegundoNome());
		assertEquals("Female", personSeven.getGenero());

	}

}
