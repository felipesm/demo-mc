package com.ifdeveloper.demomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ifdeveloper.demomc.domain.Categoria;
import com.ifdeveloper.demomc.repositories.CategoriaRepository;

@SpringBootApplication
public class DemoMcApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository repositorio;

	public static void main(String[] args) {
		SpringApplication.run(DemoMcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria categoria1 = new Categoria(null, "Informática");
		Categoria categoria2 = new Categoria(null, "Escritório");
		
		repositorio.saveAll(Arrays.asList(categoria1, categoria2));
		
	}

}
