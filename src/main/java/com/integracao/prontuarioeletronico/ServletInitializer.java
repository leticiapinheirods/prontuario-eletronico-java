package com.integracao.prontuarioeletronico;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
//Permitir que a aplicação Spring Boot seja inicializada dentro de um servidor de aplicação Java tradicional.
public class ServletInitializer extends SpringBootServletInitializer {

	@Override // Configura a aplicação para rodar como WAR em servidores externos
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ProntuarioeletronicoApplication.class);
	}       // Indica a classe principal que contém o método main 

}
