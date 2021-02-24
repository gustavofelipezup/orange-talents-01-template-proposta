package com.zup.br.proposta.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	@OneToOne
	private Bloqueio bloqueio;
	@Enumerated(EnumType.STRING)
	private StatusCartao statusCartao = StatusCartao.DESBLOQUEADO;

	public Cartao() {
		super();
	}

	public Cartao(String numeroCartao, Proposta proposta, LocalDateTime emitidoEm) {
		super();
		this.numeroCartao = numeroCartao;
		this.proposta = proposta;
		this.emitidoEm = emitidoEm;
	}
	
	public void bloquear(Bloqueio bloqueio) {
		this.bloqueio = bloqueio;
		
	}
	
	public void incluiBloqueios(Bloqueio bloqueio2) {
		// TODO Auto-generated method stub
		
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

	public StatusCartao getStatusCartao() {
		return statusCartao;
	}
	
	public void setProposta(Proposta proposta) {
		this.proposta = proposta;
	}
	
	public enum StatusCartao {
		BLOQUEADO, DESBLOQUEADO;
		
		public static StatusCartao resultadoPara(String resultadoBloqueio) {
			if (resultadoBloqueio.equals("BLOQUEADO"))
				return BLOQUEADO;
			
			return DESBLOQUEADO;
		}

	}
}
