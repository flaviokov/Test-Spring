package br.com.booksapi.resources;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.booksapi.domain.Comentario;
import br.com.booksapi.domain.Livro;
import br.com.booksapi.service.LivroService;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/v1/livros")
@Api(value = "Livro",produces = "application/json")
public class LivroResource {

	@Autowired
	private LivroService service;
	
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Livro>> list() {
		return ResponseEntity.ok(this.service.listar());
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> create(@Valid @RequestBody Livro livro) {
		Livro livroSalvo = this.service.salvar(livro);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}").buildAndExpand(livroSalvo.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Livro> findById(@PathVariable("id") Long id) {
		
		CacheControl cache = CacheControl.maxAge(30, TimeUnit.SECONDS);
		return ResponseEntity.status(HttpStatus.OK).cacheControl(cache).body(this.service.busca(id));		
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {		
	    this.service.deletar(id);		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody Livro livro, @PathVariable("id") Long id) {
		livro.setId(id);
		this.service.atualizar(livro);				
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
	public ResponseEntity<Void> updatePartial(@RequestBody Map<String, String> livroPartial, @PathVariable("id") Long id) {
		
		this.service.atualizarPartial(id, livroPartial);
	 	
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}/comentarios", method = RequestMethod.POST)
	public ResponseEntity<Void> createComentario(@RequestBody Comentario comentario, @PathVariable("id") Long livroId) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		comentario.setUsuario(authentication.getName());
		this.service.addComentario(livroId, comentario);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}/comentarios", method = RequestMethod.GET)
	public ResponseEntity<List<Comentario>> listaComentarios(@PathVariable("id") Long id) {
		List<Comentario> listarComentarios = this.service.listarComentarios(id);
		return ResponseEntity.status(HttpStatus.OK).body(listarComentarios);
	}
}
