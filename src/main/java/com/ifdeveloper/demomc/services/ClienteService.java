package com.ifdeveloper.demomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ifdeveloper.demomc.domain.Cliente;
import com.ifdeveloper.demomc.dto.ClienteDTO;
import com.ifdeveloper.demomc.repositories.ClienteRepository;
import com.ifdeveloper.demomc.services.exceptions.DataIntegrityException;
import com.ifdeveloper.demomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repositorio;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> cliente = repositorio.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado! Id pesquisado: " + id +
				", Tipo: " + Cliente.class.getName()));
	}
	
	public Cliente atualizar(Cliente cliente) {

		Cliente clienteDados = buscar(cliente.getId());
		
		atualizarDados(clienteDados, cliente);

		return repositorio.save(clienteDados);
	}
	
	private void atualizarDados(Cliente clienteDados, Cliente cliente) {
		clienteDados.setNome(cliente.getNome());
		clienteDados.setEmail(cliente.getEmail());
	}

	public void deletar(Integer id) {

		try {

			buscar(id);
			repositorio.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir o Cliente, pois ele tem relações com outras informações!");
		}

	}

	public List<Cliente> listar() {

		return repositorio.findAll();
	}

	public Page<Cliente> listarPaginado(Integer pagina, Integer quantidade, String ordenadoPor, String ordem) {

		PageRequest pageRequest = PageRequest.of(pagina, quantidade, Direction.valueOf(ordem), ordenadoPor);

		return repositorio.findAll(pageRequest);
	}

	public Cliente instanciarCliente(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
	}
	
}
