package br.com.cotiinformatica.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
public class Empresa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer idEmpresa;

	@Column(length = 100, nullable = false)
	private String nomeFantasia;

	@Column(length = 100, nullable = false, unique = true)
	private String razaoSocial;

	@Column(length = 25, nullable = false, unique = true)
	private String cnpj;

	@OneToMany(mappedBy = "empresa")
	private List<Funcionario> funcionarios;
}

