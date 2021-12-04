package br.com.cotiinformatica.responses;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmpresaGetResponse {

	private Integer idEmpresa;
	private String nomeFantasia;
	private String razaoSocial;
	private String cnpj;
}
