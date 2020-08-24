package com.ifdeveloper.demomc.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ifdeveloper.demomc.domain.Pedido;
import com.ifdeveloper.demomc.services.PedidoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Pedidos", description = "Recurso para operações com a entidade Pedido")
@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService service;
	
	@ApiOperation(value = "Consulta os dados de um determinado Pedido")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscar(@PathVariable Integer id) {
		
		Pedido pedido = service.buscar(id);
		
		return ResponseEntity.ok().body(pedido);
	}
	
	@ApiOperation(value = "Insere um novo Pedido")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> inserir(@Valid @RequestBody Pedido pedido) {
		
		pedido = service.inserir(pedido);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pedido.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@ApiOperation(value = "Lista os Pedidos de forma paginada")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<Pedido>> listarPaginado(
			@RequestParam(value = "pagina", defaultValue = "0") Integer pagina, 
			@RequestParam(value = "quantidade", defaultValue = "24") Integer quantidade,
			@RequestParam(value = "ordenadoPor", defaultValue = "dataHora") String ordenadoPor,
			@RequestParam(value = "ordem", defaultValue = "DESC") String ordem) {
		
		Page<Pedido> pedidos = service.listarPaginado(pagina, quantidade, ordenadoPor, ordem);
		return ResponseEntity.ok().body(pedidos);
	}
	
}
