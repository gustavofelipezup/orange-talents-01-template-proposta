package com.zup.br.proposta.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

import com.zup.br.proposta.dto.BiometriaDto;
import com.zup.br.proposta.model.Biometria;
import com.zup.br.proposta.model.Cartao;

@RestController	
public class BiometriaController {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	@PostMapping("/cartoes/{id}/biometrias")
	public ResponseEntity<?> cadastra(@PathVariable Long id, @RequestBody @Valid BiometriaDto request, UriComponentsBuilder uriBuilder) {
		Cartao cartao = em.find(Cartao.class, id);
		
		if(cartao == null)
			return ResponseEntity.notFound().build();
		
		if(!request.estaEmBase64()) {
			FieldError fieldError = new FieldError("biometria", "biometria", "A biometria está inválida");
			return ResponseEntity.badRequest().body(fieldError);
		}
		
		Biometria biometria = new Biometria(cartao, request.getText());
		em.persist(biometria);
		
		URI location = uriBuilder.path("/cartoes/{idCartao}/biometrias/{idBiometria}").buildAndExpand(cartao.getId(), biometria.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
}
