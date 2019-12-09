package com.ifdeveloper.demomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.ifdeveloper.demomc.domain.enums.TipoCliente;
import com.ifdeveloper.demomc.dto.NovoClienteDTO;
import com.ifdeveloper.demomc.resources.exceptions.FieldMessage;
import com.ifdeveloper.demomc.services.validation.utils.ValidaCpfCNPJ;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, NovoClienteDTO> {

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

		for (FieldMessage e : lista) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMensagem()).addPropertyNode(e.getNomeCampo())
					.addConstraintViolation();
		}
		
		return lista.isEmpty();
	}

}
