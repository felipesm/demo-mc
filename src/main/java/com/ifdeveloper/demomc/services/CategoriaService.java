package com.ifdeveloper.demomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ifdeveloper.demomc.domain.Categoria;
import com.ifdeveloper.demomc.repositories.CategoriaRepository;
import com.ifdeveloper.demomc.services.exceptions.DataIntegrityException;
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

	public Categoria inserir(Categoria categoria) {
		categoria.setId(null);
		return repositorio.save(categoria);
	}

	public Categoria atualizar(Categoria categoria) {
		
		buscar(categoria.getId());		
		
		return repositorio.save(categoria);
	}

	public void deletar(Integer id) {
		
		try {
			
			buscar(id);
			repositorio.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir a categoria que possui produtos!");
		}
		
	}

	public List<Categoria> listar() {
		
		return repositorio.findAll();
	}
	
	public Page<Categoria> listarPaginado(Integer pagina, Integer quantidade, String ordenadoPor, String ordem) {
		
		PageRequest pageRequest = PageRequest.of(pagina, quantidade, Direction.valueOf(ordem), ordenadoPor);
		
		return repositorio.findAll(pageRequest);
	}

}
