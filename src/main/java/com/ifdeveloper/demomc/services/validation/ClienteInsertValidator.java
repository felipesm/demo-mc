package com.ifdeveloper.demomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.ifdeveloper.demomc.domain.Cliente;
import com.ifdeveloper.demomc.domain.enums.TipoCliente;
import com.ifdeveloper.demomc.dto.NovoClienteDTO;
import com.ifdeveloper.demomc.repositories.ClienteRepository;
import com.ifdeveloper.demomc.resources.exceptions.FieldMessage;
import com.ifdeveloper.demomc.services.validation.utils.ValidaCpfCNPJ;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, NovoClienteDTO> {
	
	@Autowired
	private ClienteRepository repositorio;

	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(NovoClienteDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> lista = new ArrayList<>();
				
		if (objDto.getTipoCliente().equals(TipoCliente.PESSOAFISICA.getCodigo()) && !ValidaCpfCNPJ.validaCPF(objDto.getNumeroInscricao())) {
			lista.add(new FieldMessage("numeroInscricao", "CPF Inválido"));
		}
		
		if (objDto.getTipoCliente().equals(TipoCliente.PESSOAJURIDICA.getCodigo()) && !ValidaCpfCNPJ.validaCNPJ(objDto.getNumeroInscricao())) {
			lista.add(new FieldMessage("numeroInscricao", "CNPJ Inválido"));
		}
		
		Cliente cliente = repositorio.findByEmail(objDto.getEmail());
		if (cliente != null) {
			lista.add(new FieldMessage("email", "Email já cadastrado"));
		}

		for (FieldMessage e : lista) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMensagem()).addPropertyNode(e.getNomeCampo())
					.addConstraintViolation();
		}
		
		return lista.isEmpty();
	}

}
