package br.com.cotiinformatica.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.cotiinformatica.entities.Empresa;

public interface IEmpresaRepository extends CrudRepository<Empresa, Integer> {

	// método para consultar os dados de 1 empresa baseado na razaoSocial
	@Query("from Empresa e where e.razaoSocial = :param")
	Empresa findByRazaoSocial(@Param("param") String razaoSocial);

	// método para consultar os dados de 1 empresa baseado no cnpj
	@Query("from Empresa e where e.cnpj = :param")
	Empresa findByCnpj(@Param("param") String cnpj);
}



