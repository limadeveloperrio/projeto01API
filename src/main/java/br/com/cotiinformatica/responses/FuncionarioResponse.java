package br.com.cotiinformatica.responses;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FuncionarioResponse {

	private Integer statusCode;
	private String mensagem;
	private FuncionarioGetResponse funcionario;
}

