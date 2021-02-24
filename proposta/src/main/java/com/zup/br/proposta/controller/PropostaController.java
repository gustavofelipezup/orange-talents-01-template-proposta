package com.zup.br.proposta.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.zup.br.proposta.core.Status;
import com.zup.br.proposta.dto.PropostaDto;
import com.zup.br.proposta.model.AcompanhamentoProposta;
import com.zup.br.proposta.model.AnaliseClient;
import com.zup.br.proposta.model.Cartao;
import com.zup.br.proposta.model.Proposta;
import com.zup.br.proposta.repositoy.CartaoRepository;
import com.zup.br.proposta.repositoy.PropostaRepository;

import feign.FeignException;
import feign.FeignException.UnprocessableEntity;

@RestController
@RequestMapping("/propostas")
public class PropostaController {
	
	private final PropostaRepository propostaRepository;
	private final CartaoRepository cartaoRepository;
	private final AnaliseClient analiseClient;
	private final AcompanhamentoProposta acompanhamentoProposta;
	
	@PersistenceContext
	private EntityManager em;
	
	public PropostaController(PropostaRepository propostaRepository, AnaliseClient analiseClient, AcompanhamentoProposta acompanhamentoProposta, CartaoRepository cartaoRepository) {
		this.propostaRepository = propostaRepository;
		this.analiseClient = analiseClient;
		this.acompanhamentoProposta = acompanhamentoProposta;
		this.cartaoRepository = cartaoRepository;
	}
	
	@PostMapping
	public ResponseEntity<?> novaProposta(@RequestBody @Valid PropostaDto propostaDto, UriComponentsBuilder uriBuilder) {
		
		Proposta proposta = new Proposta(propostaDto.getDocumento(), propostaDto.getNome(), 
				propostaDto.getEndereco(), propostaDto.getEmail(), propostaDto.getSalario());
		
		var findDocumento = propostaRepository.findByDocumento(proposta.getDocumento());
		
		
		if (findDocumento.isPresent())
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
		
		propostaRepository.save(proposta);
		
		AnaliseClient.ConsultaStatusRequest requisicao = new AnaliseClient.ConsultaStatusRequest(proposta);
		
		try {
			AnaliseClient.ConsultaStatusResponse resposta = analiseClient.consulta(requisicao);
			proposta.atualizaStatus(resposta.getResultadoSolicitacao());
		} catch (UnprocessableEntity e) {
			proposta.atualizaStatus("COM_RESTRICAO");
		}	

		propostaRepository.save(proposta);
		

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/propostas/{id}")
				.buildAndExpand(proposta.getId())
				.toUri();
		
		
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/cartoes/{id}")
	public ResponseEntity<?> consultaCartao(@PathVariable(value = "id") Long propostaId) {
		
		Optional<Proposta> proposta = propostaRepository.findById(propostaId);
		if(proposta.isPresent()) {
//			System.out.println("Proposta gerada.");
			
			AcompanhamentoProposta.ConsultaCartaoRequest requisicao = new AcompanhamentoProposta.ConsultaCartaoRequest(proposta.get());
			try {
				AcompanhamentoProposta.ConsultaCartaoResponse resposta = acompanhamentoProposta.consulta(requisicao.getIdProposta());
				Cartao cartao = new Cartao(resposta.getId(), proposta.get(), resposta.getEmitidoEm());
				cartao.setProposta(proposta.get());
				cartaoRepository.save(cartao);
				return ResponseEntity.status(HttpStatus.OK).body(resposta);
			} catch (FeignException.NotFound e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@Scheduled(fixedRate = 10000)
	public void CartaoRefresh() {
		var proposta = propostaRepository.findByStatus(Status.ELEGIVEL);
		if (proposta.isEmpty()) {
//			System.out.println("Nenhuma proposta.");
		}
		
		proposta.forEach(p -> {
			AcompanhamentoProposta.NovoCartaoRequest requisicao = new AcompanhamentoProposta.NovoCartaoRequest(p);
					
			try {
				AcompanhamentoProposta.NovoCartaoResponse resposta = acompanhamentoProposta.consulta(requisicao);
				p.setProcessado();
				propostaRepository.save(p);
//				System.out.println("Proposta processada: " + p.getDocumento());		
			} catch (UnprocessableEntity e) {
				return;
			}
		});
	}
}
