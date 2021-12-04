package br.com.cotiinformatica.controllers;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.cotiinformatica.entities.Empresa;
import br.com.cotiinformatica.entities.Funcionario;
import br.com.cotiinformatica.repositories.IEmpresaRepository;
import br.com.cotiinformatica.repositories.IFuncionarioRepository;
import br.com.cotiinformatica.requests.FuncionarioPostRequest;
import br.com.cotiinformatica.responses.EmpresaGetResponse;
import br.com.cotiinformatica.responses.FuncionarioGetResponse;
import br.com.cotiinformatica.responses.FuncionarioResponse;

@Controller
@Transactional
public class FuncionarioController {

	private static final String ENDPOINT = "/api/funcionarios";

	@Autowired
	private IFuncionarioRepository funcionarioRepository;

	@Autowired
	private IEmpresaRepository empresaRepository;
	
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<FuncionarioResponse> post(@RequestBody FuncionarioPostRequest request) {
		
		FuncionarioResponse response = new FuncionarioResponse();
		
		try {
			
			//verificar se o cpf informado já está cadastrado para um funcionário
			if(funcionarioRepository.findByCpf(request.getCpf()) != null) {
				
				response.setStatusCode(400); //BAD REQUEST
				response.setMensagem("O CPF '" + request.getCpf() + "' já está cadastrado, tente outro.");
				
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body(response);
			}
			
			//verificar se a matricula informada já está cadastrado para um funcionário
			if(funcionarioRepository.findByMatricula(request.getMatricula()) != null) {
				
				response.setStatusCode(400); //BAD REQUEST
				response.setMensagem("A matrícula '" + request.getMatricula() + "' já está cadastrada, tente outro.");
				
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body(response);
			}
			
			//verificar se a empresa informada não existe na base de dados
			Optional<Empresa> empresa = empresaRepository.findById(request.getIdEmpresa());
			if(empresa.isEmpty()) {
				
				response.setStatusCode(400); //BAD REQUEST
				response.setMensagem("A empresa informada não foi encontrada.");
				
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body(response);
			}
			
			Funcionario funcionario = new Funcionario();
			funcionario.setNome(request.getNome());
			funcionario.setCpf(request.getCpf());
			funcionario.setMatricula(request.getMatricula());
			funcionario.setDataAdmissao(request.getDataAdmissao());
			funcionario.setEmpresa(empresa.get());
			
			//cadastrando o funcionário
			funcionarioRepository.save(funcionario);
			
			//retornar os dados do funcionario cadastrado..
			response.setStatusCode(201); //CREATED
			response.setMensagem("Funcionário cadastrado com sucesso.");			
			response.setFuncionario(mapFuncionario(funcionario));
			
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.body(response);
			
		}
		catch(Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(response);
		}
	}
	
	private FuncionarioGetResponse mapFuncionario(Funcionario funcionario) {
		
		FuncionarioGetResponse getResponse = new FuncionarioGetResponse();
		
		getResponse.setIdFuncionario(funcionario.getIdFuncionario());
		getResponse.setNome(funcionario.getNome());
		getResponse.setCpf(funcionario.getCpf());
		getResponse.setMatricula(funcionario.getMatricula());
		getResponse.setDataAdmissao(funcionario.getDataAdmissao());
		
		getResponse.setEmpresa(new EmpresaGetResponse());
		getResponse.getEmpresa().setIdEmpresa(funcionario.getEmpresa().getIdEmpresa());
		getResponse.getEmpresa().setNomeFantasia(funcionario.getEmpresa().getNomeFantasia());
		getResponse.getEmpresa().setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
		getResponse.getEmpresa().setCnpj(funcionario.getEmpresa().getCnpj());
		
		return getResponse;
	}
}

