package com.zup.br.proposta.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Cartao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime emitidoEm;
	private String titular;
	private String biometria;
	private BigDecimal limite;
	private Long idProposta;
	@OneToOne
	private Proposta proposta;
	


	public Long getId() {
		return id;
	}

	public LocalDateTime getEmitidoEm() {
		return emitidoEm;
	}

	public String getTitular() {
		return titular;
	}

	public String getBiometria() {
		return biometria;
	}


	public BigDecimal getLimite() {
		return limite;
	}

	public Long getIdProposta() {
		return idProposta;
	}
	
	
}
