package br.com.booksapi.service;

import java.beans.FeatureDescriptor;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.booksapi.domain.Comentario;
import br.com.booksapi.domain.Livro;
import br.com.booksapi.repository.ComentarioRepository;
import br.com.booksapi.repository.LivroRepository;
import br.com.booksapi.service.exception.LivroNaoEncontradoException;

@Service
public class LivroService {

	@Autowired
	private LivroRepository livroRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public List<Livro> listar() {
		return this.livroRepository.findAll();
	}
	
	public Livro busca(Long id) {
		return this.livroRepository.findById(id) 
				.orElseThrow(() -> { throw new LivroNaoEncontradoException("Not Found");});
	}
	
	public Livro salvar(Livro livro) {
		livro.setId(null);
		return this.livroRepository.save(livro);
	}
	
	public void deletar(Long id) {
		try {
			this.livroRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			throw new LivroNaoEncontradoException("Not Found");
		}
	}
	
	public void atualizar(Livro livro) {
		verificarExistencia(livro);
		this.livroRepository.saveAndFlush(livro);
	}
	
	public void atualizarPartial(Long id, Map<String, String> partial) {
		Livro busca = busca(id);
		Livro convertValue = objectMapper.convertValue(partial, Livro.class);
		BeanUtils.copyProperties(convertValue, busca,getNullPropertyNames(convertValue));
		
		this.livroRepository.saveAndFlush(busca);
	}
	
	
	public static String[] getNullPropertyNames(Object source) {
	    final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
	    return Stream.of(wrappedSource.getPropertyDescriptors())
	            .map(FeatureDescriptor::getName)
	            .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
	            .toArray(String[]::new);
	}
	
	public Comentario addComentario(Long livroId, Comentario comnetario) {
		Livro livro = busca(livroId);
		comnetario.setLivro(livro);
		comnetario.setData(new Date());
		return this.comentarioRepository.save(comnetario);
	}
	
	
	public List<Comentario> listarComentarios(Long livroId) {
		Livro livro = busca(livroId);
		return livro.getComentarios();
	}
	
	private void verificarExistencia(Livro livro) {
		busca(livro.getId());
	}
	
	
}
