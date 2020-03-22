package com.ifdeveloper.demomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.ifdeveloper.demomc.domain.Pedido;
import com.ifdeveloper.demomc.services.interfaces.EmailService;

public abstract class EmailServiceAbstract implements EmailService {
	
	@Value("${default.email.sender}")
	private String emailEnvio;
	
	private static final String ASSUNTO_EMAIL = "Pedido Confirmado: Código %s";
	
	@Override
	public void enviarConfirmacaoPedido(Pedido pedido) {

		SimpleMailMessage message = prepararMensagemDoPedido(pedido);
		enviarEmail(message);
	}
	
	protected SimpleMailMessage prepararMensagemDoPedido(Pedido pedido) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(pedido.getCliente().getEmail());
		message.setFrom(emailEnvio);
		message.setSubject(String.format(ASSUNTO_EMAIL, pedido.getId().toString()));
		message.setSentDate(new Date(System.currentTimeMillis()));
		message.setText(pedido.toString());
		
		return message;
	}
	
	

}
