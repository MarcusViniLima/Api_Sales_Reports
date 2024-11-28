package com.example.venda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VendaApplication {

	public static void main(String[] args) {
		SpringApplication.run(VendaApplication.class, args);
		System.out.println("===========================================");
		System.out.println(" Bem-vindo ao Sistema de Vendas da Rede de Lojas! 🚀 ");
		System.out.println(" Gerencie clientes, produtos e vendas de forma eficiente! ");
		System.out.println("===========================================");

		        // Hook para exibir mensagem ao encerrar o sistema
				Runtime.getRuntime().addShutdownHook(new Thread(() -> {
					System.out.println("===========================================");
					System.out.println(" Obrigado por usar o Sistema de Vendas! 👋 ");
					System.out.println(" Até a próxima! ");
					System.out.println("===========================================");
				}));
	}

}
