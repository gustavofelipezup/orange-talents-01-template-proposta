package com.zup.br.proposta.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Aviso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String destino;
	@Future
	private LocalDate dataRetorno;
	@CreationTimestamp
	private LocalDateTime instanteAviso;
	@NotNull
	@ManyToOne
	private Cartao cartao;
	private String ipCliente;
	private String userAgent;
	
	public Aviso(Cartao cartao, @NotBlank String destino, @Future LocalDate dataRetorno, String ipCliente, String userAgent) {
		this.destino = destino;
		this.dataRetorno = dataRetorno;
		this.ipCliente = ipCliente;
		this.userAgent = userAgent;
		this.cartao = cartao;
	}
	
	public String getDestino() {
		return destino;
	}

	public LocalDate getDataRetorno() {
		return dataRetorno;
	}

	public LocalDateTime getInstanteAviso() {
		return instanteAviso;
	}

	public String getIpCliente() {
		return ipCliente;
	}

	public String getUserAgent() {
		return userAgent;
	}
	
	public Aviso() {
		super();
	}
	
}
