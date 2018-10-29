package br.com.booksapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.booksapi.domain.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long>{

}
