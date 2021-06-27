package com.ifdeveloper.demomc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifdeveloper.demomc.domain.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
	
	@Transactional(readOnly = true)
	@Query("SELECT cat FROM Categoria cat WHERE lower(cat.nome) like %:nome%")
	Page<Categoria> findByNomeContaining(String nome, Pageable pageRequest);

}
