package com.casanovajava.notificacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication junta três coisas: configuração automática, varredura de componentes
// (@Service, @Component...) a partir deste pacote para baixo, e esta classe como configuração.
// Por isso esta classe fica no pacote RAIZ: o que estiver fora dele não é encontrado.
@SpringBootApplication
public class NotificacaoApplication {

	// Ponto de entrada do Java: é aqui que a aplicação começa
	public static void main(String[] args) {
		// Sobe o Spring, o Tomcat embutido (porta 8080) e carrega todos os beans
		SpringApplication.run(NotificacaoApplication.class, args);
	}

}

