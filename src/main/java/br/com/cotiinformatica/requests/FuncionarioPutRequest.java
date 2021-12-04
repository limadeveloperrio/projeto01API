package br.com.cotiinformatica.requests;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FuncionarioPutRequest {

	private Integer idFuncionario;
	private String nome;
	private Date dataAdmissao;
	private Integer idEmpresa;
}
