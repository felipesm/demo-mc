package com.ifdeveloper.demomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifdeveloper.demomc.domain.Cidade;
import com.ifdeveloper.demomc.domain.Cliente;
import com.ifdeveloper.demomc.domain.Endereco;
import com.ifdeveloper.demomc.domain.enums.Perfil;
import com.ifdeveloper.demomc.domain.enums.TipoCliente;
import com.ifdeveloper.demomc.dto.ClienteDTO;
import com.ifdeveloper.demomc.dto.NovoClienteDTO;
import com.ifdeveloper.demomc.repositories.ClienteRepository;
import com.ifdeveloper.demomc.repositories.EnderecoRepository;
import com.ifdeveloper.demomc.security.UserSecurity;
import com.ifdeveloper.demomc.services.exceptions.AuthorizationException;
import com.ifdeveloper.demomc.services.exceptions.DataIntegrityException;
import com.ifdeveloper.demomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repositorio;
	
	@Autowired
	private EnderecoRepository enderecoRepositorio;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	public Cliente buscar(Integer id) {
		
		UserSecurity userSecurity = UserService.authenticated();
		
		if (userSecurity == null || !userSecurity.hasHole(Perfil.ADMIN) && !id.equals(userSecurity.getId())) {
			throw new AuthorizationException("Acesso Negado");
		}
		
		Optional<Cliente> cliente = repositorio.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado! Id pesquisado: " + id +
				", Tipo: " + Cliente.class.getName()));
	}
	
	public Cliente buscarPorEmail(String email) {
	
		UserSecurity userSecurity = UserService.authenticated();
		
		if (userSecurity == null || !userSecurity.hasHole(Perfil.ADMIN) && !email.equals(userSecurity.getUsername())) {
			throw new AuthorizationException("Acesso Negado");
		}
		
		Cliente cliente = repositorio.findByEmail(email);
		
		if (cliente == null) {
			throw new ObjectNotFoundException("Cliente não encontrado! Email pesquisado: " + email +
					", Tipo: " + Cliente.class.getName());
		}
		
		return cliente;
	}
	
	@Transactional
	public Cliente inserir(Cliente cliente) {
		cliente.setId(null);
		cliente = repositorio.save(cliente); 
		enderecoRepositorio.saveAll(cliente.getEnderecos());
		
		return cliente;
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
			throw new DataIntegrityException("Não é possível excluir o Cliente, pois há pedidos relacionados.");
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
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null, null);
	}
	
	public Cliente instanciarCliente(NovoClienteDTO clienteDTO) {
		Cliente cliente = new Cliente(null, clienteDTO.getNome(), clienteDTO.getEmail(), clienteDTO.getNumeroInscricao(), TipoCliente.toEnum(clienteDTO.getTipoCliente()), bcrypt.encode(clienteDTO.getSenha()));
		Cidade cidade = new Cidade(clienteDTO.getIdCidade(), null, null);
		Endereco endereco = new Endereco(null, clienteDTO.getLogradouro(), clienteDTO.getNumero(), clienteDTO.getComplemento(), clienteDTO.getBairro(), clienteDTO.getCep(), cliente, cidade);
		
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(clienteDTO.getTelefoneCelular());
		
		if (clienteDTO.getTelefoneFixo() != null) {
			cliente.getTelefones().add(clienteDTO.getTelefoneFixo());
		}

		if (clienteDTO.getTelefoneComercial() != null) {
			cliente.getTelefones().add(clienteDTO.getTelefoneComercial());
		}
		
		return cliente;
	}
	
}
