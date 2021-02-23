package com.zup.br.proposta.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Cartao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String numeroCartao;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Proposta", referencedColumnName = "id")
	private Proposta proposta;
	private LocalDateTime emitidoEm;

	public Cartao() {
		super();
	}

	public Cartao(String numeroCartao, Proposta proposta, LocalDateTime emitidoEm) {
		super();
		this.numeroCartao = numeroCartao;
		this.proposta = proposta;
		this.emitidoEm = emitidoEm;
	}

	public Long getId() {
		return id;
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public Proposta getProposta() {
		return proposta;
	}

	public LocalDateTime getEmitidoEm() {
		return emitidoEm;
	}
	
}
