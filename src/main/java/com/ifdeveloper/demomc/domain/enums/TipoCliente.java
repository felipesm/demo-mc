package com.ifdeveloper.demomc.domain.enums;

public enum TipoCliente {
	PESSOAFISICA(1, "Pessoa Física"), PESSOAJURIDICA(2, "Pessoa Jurídica");
	
	public int codigo;
	private String descricao;
	
	private TipoCliente(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static TipoCliente toEnum(Integer codigo) {
		for (TipoCliente tipo : TipoCliente.values()) {
			
			if (codigo.equals(tipo.codigo)) {
				return tipo;
			}			
		}
		
		return null;
	}
}

