package com.ifdeveloper.demomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ifdeveloper.demomc.domain.Categoria;
import com.ifdeveloper.demomc.domain.Produto;
import com.ifdeveloper.demomc.repositories.CategoriaRepository;
import com.ifdeveloper.demomc.repositories.ProdutoRepository;
import com.ifdeveloper.demomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepositorio;
	
	@Autowired
	private CategoriaRepository categoriaRepositorio;
	
	public Produto buscar(Integer id) {
		Optional<Produto> pedido = produtoRepositorio.findById(id);
		return pedido.orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado! Id pesquisado: " + id +
				", Tipo: " + Categoria.class.getName()));
	}
	
	public Page<Produto> listarPaginado(String nome, List<Integer> ids, Integer pagina, Integer quantidade, String ordenadoPor, String ordem) {
		
		PageRequest pageRequest = PageRequest.of(pagina, quantidade, Direction.valueOf(ordem), ordenadoPor);
		List<Categoria> categorias = categoriaRepositorio.findAllById(ids);
		return produtoRepositorio.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}

}
