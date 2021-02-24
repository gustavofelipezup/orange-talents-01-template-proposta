package com.zup.br.proposta.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.zup.br.proposta.model.Bloqueio;
import com.zup.br.proposta.model.BloqueioClient;
import com.zup.br.proposta.model.Cartao;
import com.zup.br.proposta.repositoy.CartaoRepository;

import feign.FeignException;



@RestController
@RequestMapping("/cartoes/{id}/bloqueios")
public class BloqueioController {
	
	private final CartaoRepository cartaoRepository;
	private final BloqueioClient bloqueioClient;
	
	public BloqueioController (CartaoRepository cartaoRepository, BloqueioClient bloqueioClient) {
		this.cartaoRepository = cartaoRepository;
		this.bloqueioClient = bloqueioClient;
	}

	@Transactional
	@PostMapping
	public ResponseEntity<?> solicitarBloqueio(@PathVariable Long id, HttpServletRequest request, UriComponentsBuilder builder) {
		System.out.println("Acessando cartão no banco de dados");
		
		Optional<Cartao> cartao = cartaoRepository.findById(id);
		if (cartao.isEmpty()) {
			System.out.println("Cartão não encontrado");
			return ResponseEntity.notFound().build();
		}
			
		String usuarioLogadoIp = request.getHeader("X-FORWARDED-FOR");
		if (usuarioLogadoIp == null) {
			usuarioLogadoIp = request.getRemoteAddr();	
		}
		
		String userAgent = request.getHeader("User-Agent");

		BloqueioClient.BloqueioResponse bloqueioResponse;
		Bloqueio bloqueio;

		BloqueioClient.BloqueioRequest bloqueioRequest = new BloqueioClient.BloqueioRequest(userAgent);
		try {
			bloqueioResponse = bloqueioClient.bloquear(bloqueioRequest);
			System.out.println(bloqueioResponse.getResultado());
			bloqueio = new Bloqueio(cartao.get(), usuarioLogadoIp, userAgent);
			cartao.get().novoBloqueio(bloqueioResponse.getResultado(), bloqueio);
			cartaoRepository.save(cartao.get());			
		} catch (FeignException.UnprocessableEntity e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Esse cartão já está bloqueado.");
		}
		
		return ResponseEntity.ok(bloqueioResponse);
	}
	
}
