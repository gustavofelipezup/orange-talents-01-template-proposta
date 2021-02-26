package com.zup.br.proposta.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.zup.br.proposta.dto.AvisoDto;
import com.zup.br.proposta.model.Aviso;
import com.zup.br.proposta.model.AvisoClient;
import com.zup.br.proposta.model.Cartao;
import com.zup.br.proposta.repositoy.CartaoRepository;

import feign.FeignException;

@RestController
@RequestMapping("/cartoes/{id}/avisos")
public class AvisoController {

	private final CartaoRepository cartaoRepository;
	private final AvisoClient avisoClient;
	
	public AvisoController(CartaoRepository cartaoRepository, AvisoClient avisoClient) {
		this.avisoClient = avisoClient;
		this.cartaoRepository = cartaoRepository;
	}
	
	@Transactional
	@PostMapping
	public ResponseEntity<?> incluirAviso(@PathVariable Long id, @RequestBody AvisoDto avisoDto, HttpServletRequest request, UriComponentsBuilder builder){
		
		Optional<Cartao> possivelCartao = cartaoRepository.findById(id);
		
		if (possivelCartao.isEmpty()) {
			System.out.println("Cartão não encontrado");
			return ResponseEntity.notFound().build();
		}
		
		Cartao cartao = possivelCartao.get();
		
		String usuarioLogadoIp = request.getHeader("X-FORWARDED-FOR");
		if (usuarioLogadoIp == null) {
			usuarioLogadoIp = request.getRemoteAddr();
		}
		
		String userAgent = request.getHeader("User-Agent");
		
		AvisoClient.AvisoResponse avisoResponse;
		Aviso aviso = new Aviso(cartao, avisoDto.getDestino(), avisoDto.getValidoAte(), usuarioLogadoIp, userAgent);
		cartaoRepository.save(cartao);
		AvisoClient.AvisoRequest avisoRequest = new AvisoClient.AvisoRequest(aviso);
		
		try {	
			avisoResponse = avisoClient.incluirAviso(cartao.getNumeroCartao(), avisoRequest);
			cartao.novoAviso(avisoResponse.getResultado(), aviso);
			cartaoRepository.save(cartao);
		}catch (FeignException e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Esse cartão já possui um aviso ativo");
		}
		
		return ResponseEntity.ok(avisoResponse);
	}
}
