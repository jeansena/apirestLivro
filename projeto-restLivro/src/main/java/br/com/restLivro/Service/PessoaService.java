package br.com.restLivro.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import br.com.restLivro.controllers.PessoaController;
import br.com.restLivro.dataDTO.PessoaVO;
import br.com.restLivro.exceptions.ExcecaoDeRecursoNaoEncontrado;
import br.com.restLivro.exceptions.necessarioexcecaonula;
import br.com.restLivro.mapeador.DozerMapper;
import br.com.restLivro.model.pessoa;
import br.com.restLivro.repository.PessoaRepository;

@Service //responsavel pela logica do serviços 
public class PessoaService {
 
	//private final AtomicLong counter = new AtomicLong();
	
	private Logger logger = Logger.getLogger(PessoaService.class.getName());
	
	@Autowired
	PessoaRepository repository; //instaciar a classe repository

	@Autowired
	PagedResourcesAssembler<PessoaVO> assembler;
	
	//metodo lista todos passado para paginação
	public PagedModel<EntityModel<PessoaVO>> findAll(Pageable pageable){
		 
		logger.info("Encontrando todas as pessoas"); //msg de informação

		var personPage = repository.findAll(pageable);

		var pessoaVOSPage = personPage.map(p -> DozerMapper.parseObject(p, PessoaVO.class));//reotrna todos do banco )

		pessoaVOSPage.map(p -> p.add(linkTo(methodOn(PessoaController.class)
				.findById(p.getKey())).withSelfRel()));

		Link link = linkTo(methodOn(PessoaController.class)
				.findAll(pageable.getPageNumber(),
						pageable.getPageSize(),
						"asc")).withSelfRel();

	    
		 return assembler.toModel(pessoaVOSPage,link);
		
	}

	//METODO PARA BUSCA NOMES POR LETRAS
	//findPessoaByName -- vem la do repository
	public PagedModel<EntityModel<PessoaVO>> findPessoaByName(String nome, Pageable pageable){

		logger.info("Encontrando  por nome"); //msg de informação

		var personPage = repository.findPessoaByName(nome, pageable);

		var pessoaVOSPage = personPage.map(p -> DozerMapper.parseObject(p, PessoaVO.class));//reotrna todos do banco )

		pessoaVOSPage.map(p -> p.add(linkTo(methodOn(PessoaController.class)
				.findById(p.getKey())).withSelfRel()));

		Link link = linkTo(methodOn(PessoaController.class)
				.findAll(pageable.getPageNumber(),
						pageable.getPageSize(),
						"asc")).withSelfRel();


		return assembler.toModel(pessoaVOSPage,link);

	}
	
	//metodo por busca por id
	//esse metodo pode me retorna um erro 
	//no caso, se o usuario passa um id não existe
	//então devemos tratar esse erro com exceção personalizada
	public PessoaVO findById(Long id) {
		 //infomação
		logger.info("encontra uma pessoa");
	
	   var entity = repository.findById(id)
			   .orElseThrow(()-> new ExcecaoDeRecursoNaoEncontrado("Nenhum registro encontrado para este ID"));
		 PessoaVO vo = DozerMapper.parseObject(entity, PessoaVO.class);
		 
		 //hateoas --links na api
		 vo.add(linkTo(methodOn(PessoaController.class).findById(id)).withSelfRel());
		 return vo;
	}
	
	
	//metodo criar uma pessoa ou cadastrar
	public PessoaVO create(PessoaVO pessoa) {
		
		if (pessoa == null) throw new necessarioexcecaonula();
		
		//informação
		logger.info("criar uma pessoa");		
		//vai retorna pessoas
		var entity = DozerMapper.parseObject(pessoa, pessoa.class);
		var vo =  DozerMapper.parseObject(repository.save(entity), PessoaVO.class);
		
		//hateoas --links na api
		vo.add(linkTo(methodOn(PessoaController.class).findById(vo.getKey())).withSelfRel());		
		return vo;

	}
	
	//metodo para atualiza pessoas
	//esse metodo pode gera erro 
	//o usuario pode atualiza um que não existe no banco
	//tratamento de erro com exceção personalizada
	public PessoaVO update(PessoaVO pessoa) {
		
		if (pessoa == null) throw new necessarioexcecaonula();
		//informação
		logger.info("Atualiza uma pessoa");
		
		//busca esse pessoa no banco e salva na variavel entity
		var entity = repository.findById(pessoa.getKey())
				.orElseThrow(() -> new ExcecaoDeRecursoNaoEncontrado("Nenhum registro encontrado para este ID"));
		
	
		entity.setNome(pessoa.getNome());
		entity.setSegundoNome(pessoa.getSegundoNome());
		entity.setEndereco(pessoa.getEndereco());
		entity.setGenero(pessoa.getGenero());
		
		
		//vai atualiza no banco
		var vo =  DozerMapper.parseObject(repository.save(entity), PessoaVO.class);
		//hateoas
		vo.add(linkTo(methodOn(PessoaController.class).findById(vo.getKey())).withSelfRel());		
		return vo;
	}
     //desabilitar Pessoa
	@Transactional
	public PessoaVO disablePessoa(Long id) {
		//infomação
		logger.info("Disabling uma pessoa");

		repository.disablePessoa(id);

		var entity = repository.findById(id)
				.orElseThrow(()-> new ExcecaoDeRecursoNaoEncontrado("Nenhum registro encontrado para este ID"));
		PessoaVO vo = DozerMapper.parseObject(entity, PessoaVO.class);

		//hateoas --links na api
		vo.add(linkTo(methodOn(PessoaController.class).findById(id)).withSelfRel());
		return vo;
	}
	
	//metodo de deleta um pessoa
	//pode gera um erro por
	//deleta um pessoa que não existe no banco
	public void delete(Long id) {
		//informação
		logger.info("Deletand uma pessoa");
	//nao retorna nada porque esse metodo e void	
		
		//vai buscar no banco e deletar
		var entity = repository.findById(id)
			.orElseThrow(()-> new ExcecaoDeRecursoNaoEncontrado("Nenhum registro encontrado para este ID"));	
	  
		//vai deletar
		repository.delete(entity);
	}

	
	
/**
	//metodo para preencher a class pessoa
	private pessoa mockPessoa(int i) {
		
		pessoa pessoas = new pessoa();//instanciando a classe pessoa
		pessoas.setId(counter.incrementAndGet());//incrementa um id
		pessoas.setNome("Jean" + i);//acressenta o nome
		pessoas.setSegundoNome("Senna " + i);//acresenta o segundo nome
		pessoas.setEndereco("endereço em Brasil " + i);//acresenta o endereço		
		pessoas.setGenero("Genero");//acresenta o genero
				
		return pessoas;
	}
**/	
	
	
}






