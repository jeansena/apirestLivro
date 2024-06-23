package br.com.restLivro.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.restLivro.model.pessoa;

// essa interface permite ao acesso ao banco
@Repository
public interface PessoaRepository extends JpaRepository<pessoa, Long> {

    // disablePessoa -- desabilitar Pessoa
    @Modifying
    @Query("UPDATE pessoa p SET p.enabled = false WHERE p.id =:id")
    void disablePessoa(@Param("id")Long id);

    //pesquisa por nomes
    //and = fernanda
    //E USADO NO SERVICE E NO CONTROLLER
    @Query("SELECT p FROM pessoa p WHERE p.nome LIKE LOWER(CONCAT ('%',:nome,'%'))")
    Page<pessoa> findPessoaByName(@Param("nome")String nome, Pageable pageable);
}
