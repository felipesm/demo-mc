package com.ifdeveloper.demomc.domain.enums;

public enum Perfil {
	
	ADMIN(1, "ROLE_ADMIN"), 
	CLIENTE(2, "ROLE_CLIENTE");
	
	public int codigo;
	private String descricao;
	
	private Perfil(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static Perfil toEnum(Integer codigo) {
		for (Perfil tipo : Perfil.values()) {
			
			if (codigo.equals(tipo.codigo)) {
				return tipo;
			}			
		}
		
		return null;
	}

}
