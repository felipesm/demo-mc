package com.ifdeveloper.demomc.domain.enums;

public enum EstadoPagamento {
	
	PENDENTE(1, "Pendente"), 
	QUITADO(2, "Quitado"), 
	CANCELADO(3, "Cancelado");
	
	public int codigo;
	private String descricao;
	
	private EstadoPagamento(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static EstadoPagamento toEnum(Integer codigo) {
		for (EstadoPagamento tipo : EstadoPagamento.values()) {
			
			if (codigo.equals(tipo.codigo)) {
				return tipo;
			}			
		}
		
		return null;
	}

}
