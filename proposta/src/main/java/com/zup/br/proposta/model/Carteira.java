package com.zup.br.proposta.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
public class Carteira {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private Cartao cartao;
	@NotBlank
	private String email;
	private StatusAssociacao associada = StatusAssociacao.DESASSOCIADA;
	
	public enum StatusAssociacao {
		ASSOCIADA, DESASSOCIADA;
		
		public static StatusAssociacao resultadoPara(String solicitacao) {
			if (solicitacao.equals("ASSOCIADA"))
				return ASSOCIADA;
			
			return DESASSOCIADA;
		}
	}
	
	
}
