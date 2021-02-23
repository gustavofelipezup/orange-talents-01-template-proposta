package com.zup.br.proposta.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Biometria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Cartao")
	private Cartao cartao;
	
	private LocalDateTime criadoEm = LocalDateTime.now();
	
	@NotBlank
	@Column(columnDefinition = "text")
	private String biometria;
	
	public Biometria(Cartao cartao, String biometria) {
		this.cartao = cartao;
		this.biometria = biometria;
	}
	
	public Long getId() {
		return id;
	}

}
