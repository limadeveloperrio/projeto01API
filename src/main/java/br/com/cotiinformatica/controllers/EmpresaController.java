package br.com.cotiinformatica.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.cotiinformatica.entities.Empresa;
import br.com.cotiinformatica.repositories.IEmpresaRepository;
import br.com.cotiinformatica.requests.EmpresaPostRequest;
import br.com.cotiinformatica.requests.EmpresaPutRequest;
import br.com.cotiinformatica.responses.EmpresaGetResponse;
import br.com.cotiinformatica.responses.EmpresaResponse;

@Transactional
@Controller
public class EmpresaController {
	
	private static final String ENDPOINT = "/api/empresas";
	
	@Autowired
	private IEmpresaRepository empresaRepository;
	
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<EmpresaResponse> post(@RequestBody EmpresaPostRequest request) {
		
		EmpresaResponse response = new EmpresaResponse();
		
		try {
			
			//verificar se a razão social informada ja está cadastrado
			if(empresaRepository.findByRazaoSocial(request.getRazaoSocial()) != null) {
				
				response.setStatusCode(400); //BAD REQUEST
				response.setMensagem("A razão social '" + request.getRazaoSocial() + "' já está cadastrada, tente outro.");
				
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body(response);
			}
						
			//verificar se o cnpj informado ja está cadastrado
			if(empresaRepository.findByCnpj(request.getCnpj()) != null) {
				
				response.setStatusCode(400); //BAD REQUEST
				response.setMensagem("O cnpj '" + request.getCnpj() + "' já está cadastrado, tente outro.");
				
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body(response);
			}
			
			Empresa empresa = new Empresa();
			
			empresa.setNomeFantasia(request.getNomeFantasia());
			empresa.setRazaoSocial(request.getRazaoSocial());
			empresa.setCnpj(request.getCnpj());
			
			empresaRepository.save(empresa);
			
			response.setStatusCode(201); //CREATED
			response.setMensagem("Empresa cadastrada com sucesso.");			
			response.setEmpresa(mapEmpresa(empresa));
			
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
	
	@RequestMapping(value = ENDPOINT, method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<EmpresaResponse> put(@RequestBody EmpresaPutRequest request){
		
		EmpresaResponse response = new EmpresaResponse();
		
		try {
			
			//pesquisar a empresa no banco de dados atraves do ID..
			Optional<Empresa> registro = empresaRepository.findById(request.getIdEmpresa());
			
			//verificar se empresa foi encontrada
			if(registro.isPresent()) {
				
				//capturando a empresa obtida no banco de dados
				Empresa empresa = registro.get();
				
				//atualizando os dados da empresa
				empresa.setNomeFantasia(request.getNomeFantasia());
				empresaRepository.save(empresa);
				
				response.setStatusCode(200);
				response.setMensagem("Empresa atualizada com sucesso.");
				response.setEmpresa(mapEmpresa(empresa));			
				
				return ResponseEntity
						.status(HttpStatus.OK)
						.body(response);
			}
			else {
				response.setStatusCode(400);
				response.setMensagem("Empresa não encontrada. O ID informado é inválido.");
				
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body(response);
			}
		}
		catch(Exception e) {
			
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(response);
		}
	}
	
	@RequestMapping(value = ENDPOINT + "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<EmpresaResponse> delete(@PathVariable("id") Integer id) {
		
		EmpresaResponse response = new EmpresaResponse();
		
		try {
			
			//capturar a empresa no banco de dados atraves do ID
			Optional<Empresa> registro = empresaRepository.findById(id);
			
			//verificar se a empresa foi encontrada
			if(registro.isPresent()) {
				
				//excluindo a empresa
				Empresa empresa = registro.get();
				empresaRepository.delete(empresa);
				
				response.setStatusCode(200);
				response.setMensagem("Empresa excluída com sucesso");
				response.setEmpresa(mapEmpresa(empresa));
				
				return ResponseEntity
						.status(HttpStatus.OK)
						.body(response);
			}
			else {
				
				response.setStatusCode(400);
				response.setMensagem("Empresa não encontrada. O ID informado é inválido.");
				
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body(response);
			}
		}
		catch(Exception e) {
			
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(response);
		}
		
	}
	
	@RequestMapping(value = ENDPOINT, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<EmpresaGetResponse>> getAll(){
		
		List<EmpresaGetResponse> response = new ArrayList<EmpresaGetResponse>();
		
		try {
			
			//consultando todas as empresas na base de dados
			List<Empresa> empresas = (List<Empresa>) empresaRepository.findAll();
			
			for(Empresa empresa : empresas) {
				response.add(mapEmpresa(empresa));
			}
			
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(response);
		}
		catch(Exception e) {
			
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(response);
		}		
	}
	
	@RequestMapping(value = ENDPOINT + "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<EmpresaGetResponse> getById(@PathVariable("id") Integer id) {
		
		EmpresaGetResponse response = null;
		
		try {
			
			//capturar a empresa no banco de dados atraves do ID
			Optional<Empresa> registro = empresaRepository.findById(id);
			
			//verificando se o registro foi encontrado
			if(registro.isPresent()) {
				
				Empresa empresa = registro.get();
				response = mapEmpresa(empresa);
				
				return ResponseEntity
						.status(HttpStatus.OK)
						.body(response);
			}
			else {
				
				return ResponseEntity
						.status(HttpStatus.NO_CONTENT)
						.body(response);
			}
		}
		catch(Exception e) {
			
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(response);			
		}
	}
	
	private EmpresaGetResponse mapEmpresa(Empresa empresa) {
		
		EmpresaGetResponse getResponse = new EmpresaGetResponse();
		
		getResponse.setIdEmpresa(empresa.getIdEmpresa());
		getResponse.setNomeFantasia(empresa.getNomeFantasia());
		getResponse.setRazaoSocial(empresa.getRazaoSocial());
		getResponse.setCnpj(empresa.getCnpj());	
		
		return getResponse;
	}

}


