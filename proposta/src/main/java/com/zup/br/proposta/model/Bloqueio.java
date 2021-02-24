package com.zup.br.proposta.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "bloqueios")
public class Bloqueio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@ManyToOne
	private Cartao cartao;
	
	@CreationTimestamp
	private LocalDateTime instante;
	
	@NotBlank
	private String ip;
	
	@NotBlank
	private String userAgent;
	
	public Bloqueio(String localAddr, String usuarioLogado, Cartao cartao) {
		this.ip = localAddr;
		this.userAgent = usuarioLogado;
		this.cartao = cartao;
	}
	
	
}
