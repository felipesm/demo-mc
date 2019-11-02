package com.ifdeveloper.demomc.resources.exceptions;

import java.io.Serializable;

public class StandardError implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer status;
	private String mensagem;
	private Long horaExcecao;
	
	public StandardError(Integer status, String mensagem, Long horaExcecao) {
		super();
		this.status = status;
		this.mensagem = mensagem;
		this.horaExcecao = horaExcecao;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Long getHoraExcecao() {
		return horaExcecao;
	}

	public void setHoraExcecao(Long horaExcecao) {
		this.horaExcecao = horaExcecao;
	}

}
