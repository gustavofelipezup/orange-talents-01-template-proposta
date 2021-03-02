package com.zup.br.proposta.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
public class Carteira {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@ManyToOne
	private Cartao cartao;
	@NotBlank
	private String email;
	@NotBlank
	@Column(unique = true)
	private String nome;
	private StatusAssociacao associada = StatusAssociacao.DESASSOCIADA;
	

	public Carteira(@NotNull Cartao cartao, @NotBlank @Email String email, @NotBlank String nome) {
		super();
		this.cartao = cartao;
		this.email = email;
		this.nome = nome;
	}
	
	public Carteira() {
		super();
	}


	public Cartao getCartao() {
		return cartao;
	}
	
	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	
	
	public void setAssociada(StatusAssociacao associada) {
		this.associada = associada;
	}

	public StatusAssociacao getAssociada() {
		return associada;
	}




	public enum StatusAssociacao {
		ASSOCIADA, DESASSOCIADA;
		
		public static StatusAssociacao resultadoPara(String solicitacao) {
			if (solicitacao.equals("ASSOCIADA"))
				return ASSOCIADA;
			
			return DESASSOCIADA;
		}
	}
	
	
}
