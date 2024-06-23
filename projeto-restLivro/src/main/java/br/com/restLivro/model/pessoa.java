package br.com.restLivro.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
//essa classe cria as tabela do banco de dados
@Entity
@Table(name="pessoa")
public class pessoa {
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome", nullable = false, length = 80)
	private String nome; //firstName	
	@Column(name = "segundo_nome", nullable = false, length = 80)
	private String segundoNome; //lastName
	@Column(nullable = false, length = 100)
	private String endereco;//adress
	@Column(nullable = false, length = 6)
	private String genero;//gender
	@Column(nullable = false)
	private Boolean enabled; //habilitada ou desabilitar
	
	public pessoa() {} //construtor vazio

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		pessoa pessoa = (pessoa) o;
		return Objects.equals(id, pessoa.id) && Objects.equals(nome, pessoa.nome) && Objects.equals(segundoNome, pessoa.segundoNome) && Objects.equals(endereco, pessoa.endereco) && Objects.equals(genero, pessoa.genero) && Objects.equals(enabled, pessoa.enabled);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nome, segundoNome, endereco, genero, enabled);
	}
}
