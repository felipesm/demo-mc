package com.ifdeveloper.demomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifdeveloper.demomc.domain.Categoria;
import com.ifdeveloper.demomc.domain.ItemPedido;
import com.ifdeveloper.demomc.domain.PagamentoComBoleto;
import com.ifdeveloper.demomc.domain.Pedido;
import com.ifdeveloper.demomc.domain.enums.EstadoPagamento;
import com.ifdeveloper.demomc.repositories.ItemPedidoRepository;
import com.ifdeveloper.demomc.repositories.PagamentoRepository;
import com.ifdeveloper.demomc.repositories.PedidoRepository;
import com.ifdeveloper.demomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepositorio;
	
	@Autowired
	private PagamentoRepository pagamentoRepositorio;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepositorio;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private ClienteService clienteService;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> pedido = pedidoRepositorio.findById(id);
		return pedido.orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado! Id pesquisado: " + id +
				", Tipo: " + Categoria.class.getName()));
	}

	@Transactional
	public Pedido inserir(Pedido pedido) {
		pedido.setId(null);
		pedido.setDataHora(new Date());
		pedido.setCliente(clienteService.buscar(pedido.getCliente().getId()));
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		
		if (pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagtoBoleto = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagtoBoleto, pedido.getDataHora());
		}
		
		pedido = pedidoRepositorio.save(pedido);
		pagamentoRepositorio.save(pedido.getPagamento());
		
		for (ItemPedido item : pedido.getItens()) {
			item.setDesconto(0.00);
			item.setProduto(produtoService.buscar(item.getProduto().getId()));
			item.setPreco(item.getProduto().getPreco());
			item.setPedido(pedido);
			
		}
		itemPedidoRepositorio.saveAll(pedido.getItens());
		System.out.println(pedido);
		
		return pedido;
	}

}
