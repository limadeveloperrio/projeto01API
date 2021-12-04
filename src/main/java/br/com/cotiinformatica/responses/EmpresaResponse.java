package br.com.cotiinformatica.responses;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmpresaResponse {

	private Integer statusCode;
	private String mensagem;
	private EmpresaGetResponse empresa;
}
