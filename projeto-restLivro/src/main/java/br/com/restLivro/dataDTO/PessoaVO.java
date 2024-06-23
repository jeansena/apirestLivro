package br.com.restLivro.dataDTO;

import java.util.Objects;

import jakarta.persistence.Column;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;

@JsonPropertyOrder({"id","nome","segundoNome","endereco","genero"})
public class PessoaVO extends RepresentationModel<PessoaVO> {
	
	//private Long id;//tem que altera por inplementa o hateoas
	@JsonProperty("id")
	@Mapping("id")
	private Long key;
	private String nome;
	private String segundoNome;
	private String endereco;
	private String genero;
	private Boolean enabled; //habilitada ou desabilitar
	
	public PessoaVO() {} //construtor vazio

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSegundoNome() {
		return segundoNome;
	}

	public void setSegundoNome(String segundoNome) {
		this.segundoNome = segundoNome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		PessoaVO pessoaVO = (PessoaVO) o;
		return Objects.equals(key, pessoaVO.key) && Objects.equals(nome, pessoaVO.nome) && Objects.equals(segundoNome, pessoaVO.segundoNome) && Objects.equals(endereco, pessoaVO.endereco) && Objects.equals(genero, pessoaVO.genero) && Objects.equals(enabled, pessoaVO.enabled);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), key, nome, segundoNome, endereco, genero, enabled);
	}
}
