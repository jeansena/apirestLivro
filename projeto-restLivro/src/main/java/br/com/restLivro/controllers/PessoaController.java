package br.com.restLivro.controllers;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.restLivro.Service.PessoaService;
import br.com.restLivro.dataDTO.PessoaVO;
import br.com.restLivro.util.MediaType;

//classe controller--ela e responsavel por
//chamar as classes serviços
//a classe controller e chamado pelo usuario 
//atraves dos verbos get post put e delete passado na URL


//@CrossOrigin //cors no controller para todo verbo//
@RestController //informa que e uma classe restcontroller
@RequestMapping("/api/pessoa/v1")//o caminho na url ex:http:../pessoa
//swagger
@Tag(name = "pessoa" ,description = "Endpoints para gerenciamento de pessoas")
public class PessoaController {

	@Autowired 
	private PessoaService service;
	
	//metodo para busca todos do banco
	@GetMapping(produces = { MediaType.APPLICATION_JSON,
			          MediaType.APPLICATION_XML,
			          MediaType.APPLICATION_YML})
	//swagger
	@Operation(summary = "Encontra todas as pessoas", description = "Encontra todas as pessoas",
	  tags = {"Pessoa"},
			responses = {
					@ApiResponse(description = "Success", responseCode = "200",
							content = {
									@Content(
											mediaType = "application/json",
											array = @ArraySchema(schema = @Schema(implementation = PessoaVO.class))
									)
							}),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			}
	)
	//tirando List e passando por paginação
	public ResponseEntity<PagedModel<EntityModel<PessoaVO>>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "12") Integer size,
			@RequestParam(value = "direction", defaultValue = "asc") String direction
	){

		var sortDirection = "desc" .equalsIgnoreCase(direction)
				? Sort.Direction.DESC : Sort.Direction.ASC;
		//nome -- nome e o mesmo da entidade
		Pageable pageable = PageRequest.of(page,size, Sort.by(sortDirection,"nome"));
		return ResponseEntity.ok(service.findAll(pageable));
	}


		///busca por nome soletrado
	@GetMapping(value = "/findPessoaByName/{nome}",
			produces = { MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML})
	//swagger
	@Operation(summary = "Encontra todas as pessoas", description = "Encontra todas as pessoas",
			tags = {"Pessoa"},
			responses = {
					@ApiResponse(description = "Success", responseCode = "200",
							content = {
									@Content(
											mediaType = "application/json",
											array = @ArraySchema(schema = @Schema(implementation = PessoaVO.class))
									)
							}),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			}
	)
	//tirando List e passando por paginação
	public ResponseEntity<PagedModel<EntityModel<PessoaVO>>> findPessoaByName(
			@PathVariable(value = "nome") String nome,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "12") Integer size,
			@RequestParam(value = "direction", defaultValue = "asc") String direction
	){

		var sortDirection = "desc" .equalsIgnoreCase(direction)
				? Sort.Direction.DESC : Sort.Direction.ASC;
		//nome -- nome e o mesmo da entidade
		Pageable pageable = PageRequest.of(page,size, Sort.by(sortDirection,"nome"));
		return ResponseEntity.ok(service.findPessoaByName(nome, pageable));
	}


	//busca pro id
	@GetMapping(value = "/{id}",			
			 produces ={MediaType.APPLICATION_JSON,
			          MediaType.APPLICATION_XML,
			          MediaType.APPLICATION_YML})
	//swagger
	@Operation(summary = "Encontra uma pessoa", description = "Encontra uma pessoa",
			tags = {"Pessoa"},
			responses = {
					@ApiResponse(description = "Success", responseCode = "200",
							content = {
									@Content(
											mediaType = "application/json",
											array = @ArraySchema(schema = @Schema(implementation = PessoaVO.class))
									)
							}),
					@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			}
	)
	public PessoaVO findById(@PathVariable(value = "id") Long id) {
		return service.findById(id);
	}
	

	//metodo de criar 
	//consumes == consumir do tipo JSON e passo no formator JSON
	//produces == produção em JSON
	@PostMapping(consumes = {MediaType.APPLICATION_JSON,
	          MediaType.APPLICATION_XML,
	          MediaType.APPLICATION_YML},
			produces = {MediaType.APPLICATION_JSON,
			          MediaType.APPLICATION_XML,
			          MediaType.APPLICATION_YML})
	//swagger
	@Operation(summary = "adiciona uma nova pessoa", description = "Adiciona uma nova Pessoa passando uma representação JSON, XML ou YML da pessoa!",
			tags = {"Pessoa"},
			responses = {
					@ApiResponse(description = "Success", responseCode = "200",
							content = {
									@Content(
											mediaType = "application/json",
											array = @ArraySchema(schema = @Schema(implementation = PessoaVO.class))
									)
							}),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			}
	)
	public PessoaVO create(@RequestBody PessoaVO pessoa) {
		return service.create(pessoa);				
	}
	
	//metodo para atualizar
	@PutMapping(consumes = {MediaType.APPLICATION_JSON,
	          MediaType.APPLICATION_XML,
	          MediaType.APPLICATION_YML},
			produces = {MediaType.APPLICATION_JSON,
			          MediaType.APPLICATION_XML,
			          MediaType.APPLICATION_YML})
	//swagger
	@Operation(summary = "Atualiza uma Pessoa",
			description = "Atualiza uma Pessoa passando uma representação JSON, XML ou YML da pessoa!",
			tags = {"Pessoa"},
			responses = {
					@ApiResponse(description = "Updated", responseCode = "200",
							content = @Content(schema = @Schema(implementation = PessoaVO.class))
					),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			}
	)
	public PessoaVO update(@RequestBody PessoaVO pessoa) {
		return service.update(pessoa);
	}

	// para desabilitar Pessoa com o PATCH
	@PatchMapping(value = "/{id}",
			produces ={MediaType.APPLICATION_JSON,
					MediaType.APPLICATION_XML,
					MediaType.APPLICATION_YML})
	//swagger
	@Operation(summary = "Encontra uma pessoa", description = "Encontra uma pessoa",
			tags = {"Pessoa"},
			responses = {
					@ApiResponse(description = "Success", responseCode = "200",
							content = {
									@Content(
											mediaType = "application/json",
											array = @ArraySchema(schema = @Schema(implementation = PessoaVO.class))
									)
							}),
					@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			}
	)
	public PessoaVO disablePessoa(@PathVariable(value = "id") Long id) {
		return service.disablePessoa(id);
	}
	
	
	//metodo para deletar
	@DeleteMapping(value = "/{id}")
	//swagger
	@Operation(summary = "Exclui uma Pessoa",
			description = "Exclui uma Pessoa passando uma representação JSON, XML ou YML da pessoa!",
			tags = {"Pessoa"},
			responses = {
					@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			}
	)
	public ResponseEntity<?> delete(@PathVariable(value = "id")Long id) {
		service.delete(id);
		
		return ResponseEntity.noContent().build(); //retorna 204 ok
	}


}


















