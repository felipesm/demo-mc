package com.ifdeveloper.demomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifdeveloper.demomc.domain.Cidade;
import com.ifdeveloper.demomc.repositories.CidadeRepository;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository repositorio;
	
	public List<Cidade> listarCidades(Integer idEstado) {
		
		return repositorio.findCidades(idEstado);
	}

}
