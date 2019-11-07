package com.ifdeveloper.demomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ifdeveloper.demomc.domain.Categoria;
import com.ifdeveloper.demomc.domain.Cidade;
import com.ifdeveloper.demomc.domain.Estado;
import com.ifdeveloper.demomc.domain.Produto;
import com.ifdeveloper.demomc.repositories.CategoriaRepository;
import com.ifdeveloper.demomc.repositories.CidadeRepository;
import com.ifdeveloper.demomc.repositories.EstadoRepository;
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

	public static void main(String[] args) {
		SpringApplication.run(DemoMcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria categoria1 = new Categoria(null, "Informática");
		Categoria categoria2 = new Categoria(null, "Escritório");
		
		Produto produto1 = new Produto(null, "Computador", 2000.00);
		Produto produto2 = new Produto(null, "Impressora", 600.00);
		Produto produto3 = new Produto(null, "Monitor", 800.00);
		
		categoria1.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3));
		categoria2.getProdutos().addAll(Arrays.asList(produto2));
		
		produto1.getCategorias().addAll(Arrays.asList(categoria1));
		produto2.getCategorias().addAll(Arrays.asList(categoria1, categoria2));
		produto3.getCategorias().addAll(Arrays.asList(categoria1));
		
		categoriaRepositorio.saveAll(Arrays.asList(categoria1, categoria2));
		produtoRepositorio.saveAll(Arrays.asList(produto1, produto2, produto3));
		
		
		Estado estado1 = new Estado(null, "Paraíba");
		Estado estado2 = new Estado(null, "São Paulo");
		
		Cidade cidade1 = new Cidade(null, "João Pessoa", estado1);
		Cidade cidade2 = new Cidade(null, "São Paulo", estado2);
		Cidade cidade3 = new Cidade(null, "Campinas", estado2);
		
		estado1.getCidades().addAll(Arrays.asList(cidade1));
		estado2.getCidades().addAll(Arrays.asList(cidade2, cidade3));
		
		estadoRepositorio.saveAll(Arrays.asList(estado1, estado2));
		cidadeRepositorio.saveAll(Arrays.asList(cidade1, cidade2, cidade3));		
		
	}

}
