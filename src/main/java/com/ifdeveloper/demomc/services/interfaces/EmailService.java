package com.ifdeveloper.demomc.services.interfaces;

import org.springframework.mail.SimpleMailMessage;

import com.ifdeveloper.demomc.domain.Pedido;

public interface EmailService {
	
	void enviarConfirmacaoPedido(Pedido pedido);

	void enviarEmail(SimpleMailMessage message);
}
