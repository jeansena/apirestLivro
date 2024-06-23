package br.com.restLivro.Service;

import br.com.restLivro.controllers.BookController;
import br.com.restLivro.dataDTO.BookVO;
import br.com.restLivro.exceptions.ExcecaoDeRecursoNaoEncontrado;
import br.com.restLivro.exceptions.necessarioexcecaonula;
import br.com.restLivro.mapeador.DozerMapper;
import br.com.restLivro.model.Book;
import br.com.restLivro.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

@Service
public class BookService {

    private Logger logger = Logger.getLogger(BookService.class.getName());

    @Autowired
    private BookRepository repository;

    @Autowired
    PagedResourcesAssembler<BookVO> assembler;

    //metodo lista todos// mudado para paginação
    public PagedModel<EntityModel<BookVO>> buscaTodos(Pageable pageable){

        //info
        logger.info("busca todos lista paginada");

        var booksPage = repository.findAll(pageable);

        var booksVos = booksPage.map(p -> DozerMapper.parseObject(p, BookVO.class));
          booksVos.map(p->p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));

        Link findAllLink = linkTo(methodOn(BookController.class)
                .findAll(pageable.getPageNumber(),
                        pageable.getPageSize(),
                        "asc")).withSelfRel();

          return assembler.toModel(booksVos, findAllLink );
    }

    //metodo busca por id
    public BookVO buscoPorId(Long id){
        //info
        logger.info("Encontrando um livro");

        var entity = repository.findById(id)
                .orElseThrow(()-> new ExcecaoDeRecursoNaoEncontrado("Nenhum registro encontrado para este ID!"));

        var vo = DozerMapper.parseObject(entity, BookVO.class);
        //hateoas
        vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return vo;
    }

    //metodo para criar
    public BookVO criar(BookVO book){

        logger.info("criar uma pessoa");

        //verificar error se passa um id invalido ou nullo
        if (book == null) throw new necessarioexcecaonula();
        var entity = DozerMapper.parseObject(book, Book.class);
        var vo = DozerMapper.parseObject(repository.save(entity),BookVO.class);
        //hateoas
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());

        return vo;

    }

    //metodo para atualizar
    public  BookVO atualizar(BookVO book){
        //verificar erro
        if (book == null) throw new necessarioexcecaonula();

        //info
        logger.info("update one book");

        //busca no banco atraves do id
        var entity = repository.findById(book.getKey())
                .orElseThrow(() -> new ExcecaoDeRecursoNaoEncontrado("Nenhum registro encontrado para este ID"));

        //atualiza no banco
        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());

        //passa de dto para entidade
        var vo = DozerMapper.parseObject(repository.save(entity),BookVO.class);
        //hateoas
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());

        return vo;

    }

    //metodo para  delete
    public void deletar(Long id){
        //info
        logger.info("deletado um book");

        //verifica ser o id exisiste ou se e nullo
        var entity = repository.findById(id)
                .orElseThrow(()->new ExcecaoDeRecursoNaoEncontrado("Nenhum registro encontrado para este ID"));

        repository.delete(entity);
    }

}
































