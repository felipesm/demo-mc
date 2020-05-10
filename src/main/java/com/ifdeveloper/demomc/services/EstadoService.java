package com.ifdeveloper.demomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifdeveloper.demomc.domain.Estado;
import com.ifdeveloper.demomc.repositories.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository repositorio;
	
	public List<Estado> listarEstados() {
		
		return repositorio.findAllByOrderByNome();
	}

}
