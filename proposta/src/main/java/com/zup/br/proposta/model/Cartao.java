package com.zup.br.proposta.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class Cartao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String numeroCartao;
	@OneToOne
	@JoinColumn(name = "Proposta", referencedColumnName = "id")
	private Proposta proposta;
	private LocalDateTime emitidoEm;
	@OneToMany(mappedBy = "cartao", cascade = CascadeType.MERGE)
	private List<Bloqueio> bloqueios = new ArrayList();
	@OneToMany(mappedBy = "cartao", cascade = CascadeType.MERGE)
	private List<Aviso> avisos = new ArrayList();
	@Enumerated(EnumType.STRING)
	private StatusCartao statusCartao = StatusCartao.DESBLOQUEADO;
	@Enumerated(EnumType.STRING)
	private StatusAviso statusAviso = StatusAviso.NAO_CRIADO;

	public Cartao(String numeroCartao, Proposta proposta, LocalDateTime emitidoEm) {
		super();
		this.numeroCartao = numeroCartao;
		this.proposta = proposta;
		this.emitidoEm = emitidoEm;
	}
	
	public void novoBloqueio(String resultado, Bloqueio bloqueio) {
		this.statusCartao = StatusCartao.resultadoPara(resultado);
		this.bloqueios.add(bloqueio);
	}
	
	public void novoAviso(String resultado, Aviso aviso) {
		this.statusAviso = StatusAviso.resultadoPara(resultado);
		this.avisos.add(aviso);
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
	
	public Cartao() {
		super();
	}
	
	public enum StatusCartao {
		BLOQUEADO, DESBLOQUEADO;
		
		public static StatusCartao resultadoPara(String solicitacao) {
			if (solicitacao.equals("BLOQUEADO"))
				return BLOQUEADO;
			
			return DESBLOQUEADO;
		}
	}

	public enum StatusAviso {
		CRIADO, NAO_CRIADO;
		
		public static StatusAviso resultadoPara(String solicitacao) {
			if (solicitacao.equals("CRIADO"))
				return CRIADO;
			
			return NAO_CRIADO;
		}
	}
}
