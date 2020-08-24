package com.ifdeveloper.demomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ifdeveloper.demomc.domain.Produto;
import com.ifdeveloper.demomc.dto.ProdutoDTO;
import com.ifdeveloper.demomc.resources.utils.URL;
import com.ifdeveloper.demomc.services.ProdutoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Produtos", description = "Recurso para operações com a entidade Produto")
@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service;
	
	@ApiOperation(value = "Consulta os dados de um determinado Produto")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscar(@PathVariable Integer id) {
		
		Produto produto = service.buscar(id);
		
		return ResponseEntity.ok().body(produto);
	}
	
	@ApiOperation(value = "Lista os Produtos de forma paginada")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> listarPaginado(
			@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "categorias", defaultValue = "") String categorias, 
			@RequestParam(value = "pagina", defaultValue = "0") Integer pagina, 
			@RequestParam(value = "quantidade", defaultValue = "24") Integer quantidade,
			@RequestParam(value = "ordenadoPor", defaultValue = "nome") String ordenadoPor,
			@RequestParam(value = "ordem", defaultValue = "ASC") String ordem) {
		
		String nomeDecodificado = URL.decodificarParametro(nome);
		List<Integer> ids = URL.converterListaInteiros(categorias);
		
		Page<Produto> produtos = service.listarPaginado(nomeDecodificado, ids, pagina, quantidade, ordenadoPor, ordem);
		Page<ProdutoDTO> produtosDTO = produtos.map(obj -> new ProdutoDTO(obj));
		
		return ResponseEntity.ok().body(produtosDTO);
	}
	
}
