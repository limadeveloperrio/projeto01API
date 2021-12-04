package br.com.cotiinformatica.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Funcionario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer idFuncionario;

	@Column(length = 150, nullable = false)
	private String nome;

	@Column(length = 20, nullable = false, unique = true)
	private String matricula;

	@Column(length = 11, nullable = false, unique = true)
	private String cpf;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataAdmissao;

	@ManyToOne // MUITOS Funcionarios para 1 Empresa
	@JoinColumn(name = "idEmpresa", nullable = false)
	private Empresa empresa;

}
