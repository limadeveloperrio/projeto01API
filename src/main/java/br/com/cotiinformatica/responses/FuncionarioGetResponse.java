package br.com.cotiinformatica.responses;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FuncionarioGetResponse {

	private Integer idFuncionario;
	private String nome;
	private String matricula;
	private String cpf;
	private Date dataAdmissao;
	private EmpresaGetResponse empresa;

}
