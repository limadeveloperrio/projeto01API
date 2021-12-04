package br.com.cotiinformatica.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmpresaPostRequest {
	private String nomeFantasia;
	private String razaoSocial;
	private String cnpj;
}