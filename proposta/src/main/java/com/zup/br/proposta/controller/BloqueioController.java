package com.zup.br.proposta.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.zup.br.proposta.model.Bloqueio;
import com.zup.br.proposta.model.Cartao;
import com.zup.br.proposta.repositoy.CartaoRepository;

import feign.FeignException;



@RestController
@RequestMapping("/cartoes/{id}/bloqueios")
public class BloqueioController {
	
	private final CartaoRepository cartaoRepository;
	
	public BloqueioController (CartaoRepository cartaoRepository) {
		this.cartaoRepository = cartaoRepository;
	}

	@Transactional
	@PutMapping
	public ResponseEntity<?> solicitarBloqueio(@PathVariable Long id, HttpServletRequest request, UriComponentsBuilder builder) {
		System.out.println("Acessando cartão no banco de dados");
		
//		Optional<Cartao> cartao = cartaoRepository.findById(id).orElseThrow(
//				() -> new IllegalStateException(String.format("Cartão não encontrado")));
		
//		String usuarioLogado = request.getHeader("User-Agent");
		
//		Bloqueio bloqueio = new Bloqueio(request.getLocalAddr(), usuarioLogadoIp, cartao);
//		cartao.bloquear(bloqueio);
		
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

		Bloqueio bloqueio;
		
		try {

			bloqueio = new Bloqueio(usuarioLogadoIp, userAgent, cartao.get());
			cartao.get().incluiBloqueios(bloqueio);
			cartaoRepository.save(cartao.get());			
		} catch (FeignException.UnprocessableEntity e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Esse cartão já está bloqueado.");
		}
		return null;
//		return ResponseEntity.ok();
	}
	
}
