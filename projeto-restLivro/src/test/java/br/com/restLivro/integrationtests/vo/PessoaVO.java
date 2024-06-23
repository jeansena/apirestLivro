package br.com.restLivro.integrationtests.vo;


import jakarta.xml.bind.annotation.XmlRootElement;


import java.util.Objects;

//@JsonPropertyOrder({"id","nome","segundoNome","endereco","genero"})
@XmlRootElement
public class PessoaVO  {
	
	//private Long id;//tem que altera por inplementa o hateoas

	private Long id;
	private String nome;
	private String segundoNome;
	private String endereco;
	private String genero;
	private Boolean enabled; //habilitada ou desabilitar
	
	public PessoaVO() {} //construtor vazio

	public Long getId() {
		return id;
	}

	public void setId(Long key) {
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
		PessoaVO pessoaVO = (PessoaVO) o;
		return Objects.equals(id, pessoaVO.id) && Objects.equals(nome, pessoaVO.nome) && Objects.equals(segundoNome, pessoaVO.segundoNome) && Objects.equals(endereco, pessoaVO.endereco) && Objects.equals(genero, pessoaVO.genero) && Objects.equals(enabled, pessoaVO.enabled);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nome, segundoNome, endereco, genero, enabled);
	}
}
