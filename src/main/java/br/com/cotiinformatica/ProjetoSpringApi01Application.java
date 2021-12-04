package br.com.cotiinformatica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * Primeira classe de configuração do projeto Spring BOOT
 * Esta classe precisa mapear o caminho de pacotes do projeto
 */
@SpringBootApplication(scanBasePackages = { "br.com.cotiinformatica" })
public class ProjetoSpringApi01Application {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoSpringApi01Application.class, args);
	}

}
