package com.ifdeveloper.demomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ifdeveloper.demomc.domain.Categoria;
import com.ifdeveloper.demomc.domain.Cidade;
import com.ifdeveloper.demomc.domain.Cliente;
import com.ifdeveloper.demomc.domain.Endereco;
import com.ifdeveloper.demomc.domain.Estado;
import com.ifdeveloper.demomc.domain.ItemPedido;
import com.ifdeveloper.demomc.domain.Pagamento;
import com.ifdeveloper.demomc.domain.PagamentoComBoleto;
import com.ifdeveloper.demomc.domain.PagamentoComCartao;
import com.ifdeveloper.demomc.domain.Pedido;
import com.ifdeveloper.demomc.domain.Produto;
import com.ifdeveloper.demomc.domain.enums.EstadoPagamento;
import com.ifdeveloper.demomc.domain.enums.TipoCliente;
import com.ifdeveloper.demomc.repositories.CategoriaRepository;
import com.ifdeveloper.demomc.repositories.CidadeRepository;
import com.ifdeveloper.demomc.repositories.ClienteRepository;
import com.ifdeveloper.demomc.repositories.EnderecoRepository;
import com.ifdeveloper.demomc.repositories.EstadoRepository;
import com.ifdeveloper.demomc.repositories.ItemPedidoRepository;
import com.ifdeveloper.demomc.repositories.PagamentoRepository;
import com.ifdeveloper.demomc.repositories.PedidoRepository;
import com.ifdeveloper.demomc.repositories.ProdutoRepository;

@SpringBootApplication
public class DemoMcApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepositorio;
	
	@Autowired
	private ProdutoRepository produtoRepositorio;
	
	@Autowired
	private EstadoRepository estadoRepositorio;
	
	@Autowired
	private CidadeRepository cidadeRepositorio;
	
	@Autowired
	private ClienteRepository clienteRepositorio;
	
	@Autowired
	private EnderecoRepository enderecoRepositorio;
	
	@Autowired
	private PedidoRepository pedidoRepositorio;
	
	@Autowired
	private PagamentoRepository pagamentoRepositorio;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepositorio;

	public static void main(String[] args) {
		SpringApplication.run(DemoMcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria categoria1 = new Categoria(null, "Informática");
		Categoria categoria2 = new Categoria(null, "Escritório");
		Categoria categoria3 = new Categoria(null, "Banho");
		Categoria categoria4 = new Categoria(null, "Móveis");
		Categoria categoria5 = new Categoria(null, "Telefones e Celulares");
		Categoria categoria6 = new Categoria(null, "Áudio");
		Categoria categoria7 = new Categoria(null, "TV e Vídeo");
		
		Produto produto1 = new Produto(null, "Computador", 2000.00);
		Produto produto2 = new Produto(null, "Impressora", 600.00);
		Produto produto3 = new Produto(null, "Monitor", 800.00);
		Produto produto4 = new Produto(null, "Mesa", 350.00);
		Produto produto5 = new Produto(null, "Xiaomi Note 7", 980.00);
		Produto produto6 = new Produto(null, "Shampoo", 7.50);
		Produto produto7 = new Produto(null, "Samsung S9", 1020.10);
		
		categoria1.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3));
		categoria2.getProdutos().addAll(Arrays.asList(produto2, produto4));
		categoria3.getProdutos().addAll(Arrays.asList(produto6));
		categoria5.getProdutos().addAll(Arrays.asList(produto5, produto7));
		
		produto1.getCategorias().addAll(Arrays.asList(categoria1));
		produto2.getCategorias().addAll(Arrays.asList(categoria1, categoria2));
		produto3.getCategorias().addAll(Arrays.asList(categoria1));
		produto4.getCategorias().addAll(Arrays.asList(categoria2, categoria4));
		produto5.getCategorias().addAll(Arrays.asList(categoria5));
		produto6.getCategorias().addAll(Arrays.asList(categoria3));
		produto7.getCategorias().addAll(Arrays.asList(categoria5));
		
		categoriaRepositorio.saveAll(Arrays.asList(categoria1, categoria2, categoria3, categoria4, categoria5, categoria6, categoria7));
		produtoRepositorio.saveAll(Arrays.asList(produto1, produto2, produto3, produto4, produto5, produto6, produto7));
		
		
		Estado estado1 = new Estado(null, "Paraíba");
		Estado estado2 = new Estado(null, "São Paulo");
		
		Cidade cidade1 = new Cidade(null, "João Pessoa", estado1);
		Cidade cidade2 = new Cidade(null, "São Paulo", estado2);
		Cidade cidade3 = new Cidade(null, "Campinas", estado2);
		
		estado1.getCidades().addAll(Arrays.asList(cidade1));
		estado2.getCidades().addAll(Arrays.asList(cidade2, cidade3));
		
		estadoRepositorio.saveAll(Arrays.asList(estado1, estado2));
		cidadeRepositorio.saveAll(Arrays.asList(cidade1, cidade2, cidade3));
		
		Cliente cliente1 = new Cliente(null, "Maria do Bairro", "mariabairro@mail.com", "10204472347", TipoCliente.PESSOAFISICA);
		cliente1.getTelefones().addAll(Arrays.asList("55 5551205", "55 5553069"));
		
		Endereco endereco1 = new Endereco(null, "Rua Cabral", "341", "Apto", "Centro", "33103090", cliente1, cidade1);
		Endereco endereco2 = new Endereco(null, "Vila Jardim", "456", "Vila", "Novo Jardim", "31010990", cliente1, cidade2);
		
		cliente1.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));
		
		clienteRepositorio.saveAll(Arrays.asList(cliente1));
		enderecoRepositorio.saveAll(Arrays.asList(endereco1, endereco2));
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido pedido1 = new Pedido(null, format.parse("13/11/2019 01:32"), cliente1, endereco1);
		Pedido pedido2 = new Pedido(null, format.parse("13/11/2019 01:34"), cliente1, endereco2);
		
		Pagamento pagamento1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedido1, "4772566110737820", 5);
		pedido1.setPagamento(pagamento1);
		
		Pagamento pagamento2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pedido2, "23711111212333333333133344444441145670001000000", format.parse("20/11/2019 15:00"), null);
		pedido2.setPagamento(pagamento2);
		
		cliente1.getPedidos().addAll(Arrays.asList(pedido1, pedido2));
		
		pedidoRepositorio.saveAll(Arrays.asList(pedido1, pedido2));
		pagamentoRepositorio.saveAll(Arrays.asList(pagamento1, pagamento2));
		
		ItemPedido itemPedido1 = new ItemPedido(pedido1, produto1, 0.00, 1, 2000.00);
		ItemPedido itemPedido2 = new ItemPedido(pedido1, produto3, 0.00, 2, 800.00);
		ItemPedido itemPedido3 = new ItemPedido(pedido2, produto2, 100.00, 1, 600.00);
		
		pedido1.getItens().addAll(Arrays.asList(itemPedido1, itemPedido2));
		pedido2.getItens().addAll(Arrays.asList(itemPedido3));
		
		produto1.getItens().addAll(Arrays.asList(itemPedido1));
		produto2.getItens().addAll(Arrays.asList(itemPedido3));
		produto3.getItens().addAll(Arrays.asList(itemPedido2));
		
		itemPedidoRepositorio.saveAll(Arrays.asList(itemPedido1, itemPedido2, itemPedido3));
	}

}
