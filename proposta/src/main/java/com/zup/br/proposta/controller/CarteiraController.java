package com.zup.br.proposta.controller;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.zup.br.proposta.dto.CarteiraDto;
import com.zup.br.proposta.model.Cartao;
import com.zup.br.proposta.model.Carteira;
import com.zup.br.proposta.model.CarteiraClient;
import com.zup.br.proposta.repositoy.CartaoRepository;

import feign.FeignException;

@RestController
@RequestMapping("/cartoes/{id}/carteiras")
public class CarteiraController {
	
	private final CartaoRepository cartaoRepository;
	private final CarteiraClient carteiraClient;
	
	public CarteiraController(CartaoRepository cartaoRepository, CarteiraClient carteiraClient) {
		this.cartaoRepository = cartaoRepository;
		this.carteiraClient = carteiraClient;
	}

	@Transactional
	@PostMapping
	public ResponseEntity<?> adicionarMetodo(@PathVariable Long id, @RequestBody CarteiraDto carteiraDto, UriComponentsBuilder builder){
	
		Optional<Cartao> possivelCartao = cartaoRepository.findById(id);
		
		if(possivelCartao.isEmpty()) {
			System.out.println("Cartão não encontrado");
			return ResponseEntity.notFound().build();
		}
		
		Cartao cartao = possivelCartao.get();
		
		CarteiraClient.CarteiraResponse carteiraResponse;
		Carteira carteira = new Carteira(cartao, carteiraDto.getEmail(), carteiraDto.getId());
		cartaoRepository.save(cartao);
		CarteiraClient.CarteiraRequest carteiraRequest = new CarteiraClient.CarteiraRequest(carteira);
		
		try {
			carteiraResponse = carteiraClient.adicionarMetodo(cartao.getNumeroCartao(), carteiraRequest);
			cartao.novoMetodo(carteiraResponse.getResultado(), carteira);
			cartaoRepository.save(cartao);
		} catch (FeignException e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Esse cartão já está associado a essa carteira");
		}
		
		return ResponseEntity.ok(carteiraResponse);
	}
	
}
