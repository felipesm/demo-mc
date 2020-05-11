package com.ifdeveloper.demomc.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> listaErros = new ArrayList<>();
	
	public ValidationError(Long timestamp, Integer status, String error, String message, String path) {
		super(timestamp, status, error, message, path);
	}

	public List<FieldMessage> getListaDeErros() {
		return listaErros;
	}
	
	public void adicionarErro(String nomeCampo, String mensagem) {
		listaErros.add(new FieldMessage(nomeCampo, mensagem));
	}

}
