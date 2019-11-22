package com.ifdeveloper.demomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifdeveloper.demomc.domain.Categoria;
import com.ifdeveloper.demomc.domain.Pedido;
import com.ifdeveloper.demomc.repositories.PedidoRepository;
import com.ifdeveloper.demomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repositorio;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> pedido = repositorio.findById(id);
		return pedido.orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado! Id pesquisado: " + id +
				", Tipo: " + Categoria.class.getName()));
	}

}
