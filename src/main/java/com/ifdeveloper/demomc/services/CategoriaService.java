package com.ifdeveloper.demomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifdeveloper.demomc.domain.Categoria;
import com.ifdeveloper.demomc.repositories.CategoriaRepository;
import com.ifdeveloper.demomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repositorio;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> categoria = repositorio.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada! Id pesquisado: " + id +
				", Tipo: " + Categoria.class.getName()));
	}

}
