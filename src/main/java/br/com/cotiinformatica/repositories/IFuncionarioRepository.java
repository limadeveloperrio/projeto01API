package br.com.cotiinformatica.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.cotiinformatica.entities.Funcionario;

public interface IFuncionarioRepository extends CrudRepository<Funcionario, Integer> {

	@Query("from Funcionario f where f.cpf = :param")
	Funcionario findByCpf(@Param("param") String cpf);

	@Query("from Funcionario f where f.matricula = :param")
	Funcionario findByMatricula(@Param("param") String matricula);

	@Query("from Funcionario f where f.dataAdmissao between :param1 and :param2")
	List<Funcionario> findByDataAdmissao(@Param("param1") Date dataMin, @Param("param2") Date dataMax);
}
