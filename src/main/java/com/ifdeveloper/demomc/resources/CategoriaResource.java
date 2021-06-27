package com.ifdeveloper.demomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ifdeveloper.demomc.domain.Categoria;
import com.ifdeveloper.demomc.dto.CategoriaDTO;
import com.ifdeveloper.demomc.resources.utils.URL;
import com.ifdeveloper.demomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscar(@PathVariable Integer id) {
		
		Categoria categoria = service.buscar(id);
		
		return ResponseEntity.ok().body(categoria);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> inserir(@Valid @RequestBody CategoriaDTO categoriaDTO) {
		Categoria categoria = service.instanciarCategoria(categoriaDTO);
		categoria = service.inserir(categoria);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizar(@Valid @RequestBody CategoriaDTO categoriaDTO, @PathVariable Integer id) {
		
		Categoria categoria = service.instanciarCategoria(categoriaDTO);		
		categoria.setId(id);
		categoria = service.atualizar(categoria);
		
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletar(@PathVariable Integer id) {
		service.deletar(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> listar() {
		List<Categoria> categorias = service.listar();
		List<CategoriaDTO> categoriasDTO = categorias.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok(categoriasDTO);
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> listarPaginado(
			@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "pagina", defaultValue = "0") Integer pagina, 
			@RequestParam(value = "quantidade", defaultValue = "24") Integer quantidade,
			@RequestParam(value = "ordenadoPor", defaultValue = "nome") String ordenadoPor,
			@RequestParam(value = "ordem", defaultValue = "ASC") String ordem) {
		
		String nomeDecodificado = URL.decodificarParametro(nome);
		
		Page<Categoria> categorias = service.listarPaginado(nomeDecodificado, pagina, quantidade, ordenadoPor, ordem);
		Page<CategoriaDTO> categoriasDTO = categorias.map(obj -> new CategoriaDTO(obj));
		
		return ResponseEntity.ok().body(categoriasDTO);
	}
}
