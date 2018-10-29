package br.com.booksapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.booksapi.domain.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long>{

}
