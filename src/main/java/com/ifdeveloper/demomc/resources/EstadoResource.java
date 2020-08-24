package com.ifdeveloper.demomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ifdeveloper.demomc.domain.Cidade;
import com.ifdeveloper.demomc.domain.Estado;
import com.ifdeveloper.demomc.dto.CidadeDTO;
import com.ifdeveloper.demomc.dto.EstadoDTO;
import com.ifdeveloper.demomc.services.CidadeService;
import com.ifdeveloper.demomc.services.EstadoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Estados", description = "Recurso para operações com a entidade Estado")
@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {
	
	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private CidadeService cidadeService;
	
	@ApiOperation(value = "Lista todos os Estados")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> listarEstados() {
		List<Estado> estados = estadoService.listarEstados();
		List<EstadoDTO> estadosDTO = estados.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(estadosDTO);
	}
	
	@ApiOperation(value = "Lista as cidades de um determinado Estado")
	@RequestMapping(value = "/{idEstado}/cidades", method = RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> listarCidadesPorEstado(@PathVariable Integer idEstado) {
		
		List<Cidade> cidades = cidadeService.listarCidades(idEstado);
		List<CidadeDTO> cidadesDTO = cidades.stream().map(cid -> new CidadeDTO(cid)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(cidadesDTO);
	}
}
