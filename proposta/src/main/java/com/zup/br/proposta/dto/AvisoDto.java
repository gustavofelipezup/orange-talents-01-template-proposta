package com.zup.br.proposta.dto;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;


public class AvisoDto {

	private LocalDate validoAte;
	private String destino;
	
	public AvisoDto(@Future LocalDate validoAte, @NotBlank String destino) {
		this.validoAte = validoAte;
		this.destino = destino;
	}
	
	public AvisoDto() {
		super();
	}

	public LocalDate getValidoAte() {
		return validoAte;
	}

	public void setValidoAte(LocalDate validoAte) {
		this.validoAte = validoAte;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}
	
	
}
