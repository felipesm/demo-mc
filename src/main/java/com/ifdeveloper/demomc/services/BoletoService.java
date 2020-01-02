package com.ifdeveloper.demomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.ifdeveloper.demomc.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date dataPedido) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dataPedido);
		calendar.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(calendar.getTime());
		
		String linhaDigitavel = "23711111212333333333133344444441170070005000999";
		pagto.setLinhaDigitavel(linhaDigitavel);
	}
}
