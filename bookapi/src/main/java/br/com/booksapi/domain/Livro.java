package br.com.booksapi.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.booksapi.domain.base.EntityBase;

@Entity
public class Livro extends EntityBase<Livro>{
	
	@NotBlank(message = "O Nome é obrigatorio.")
	private String nome;
	
	@JsonInclude(value = Include.NON_NULL)
	private Date publicacao;
	
	@JsonInclude(value = Include.NON_NULL)
	@NotBlank(message = "A Editora é obrigatoria.")
	private String editora;
	
	@JsonInclude(value = Include.NON_NULL)
	@Size(max = 122, message = "O Tamanho maximo do resumo é de 122")
	private String resumo;
	
	@JsonInclude(value = Include.NON_NULL)
	@NotBlank(message = "O Autor é obrigatorio.")
	private String autor;
	
	@JsonInclude(value = Include.NON_EMPTY)
	@OneToMany(mappedBy = "livro")
	private List<Comentario> comentarios;
	
	public Livro() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Livro(String nome) {
		super();
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getPublicacao() {
		return publicacao;
	}
	public void setPublicacao(Date publicacao) {
		this.publicacao = publicacao;
	}
	public String getEditora() {
		return editora;
	}
	public void setEditora(String editora) {
		this.editora = editora;
	}
	public String getResumo() {
		return resumo;
	}
	public void setResumo(String resumo) {
		this.resumo = resumo;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public List<Comentario> getComentarios() {
		return comentarios;
	}
	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	
	
	
	
	
}
