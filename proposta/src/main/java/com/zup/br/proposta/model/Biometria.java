package com.zup.br.proposta.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Biometria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@ManyToOne
	private Cartao cartao;
	
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
