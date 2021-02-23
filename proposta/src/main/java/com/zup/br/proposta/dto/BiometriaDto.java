package com.zup.br.proposta.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.codec.binary.Base64;

public class BiometriaDto {

	private String biometria;
	
	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public BiometriaDto(@NotBlank String biometria) {
		this.biometria = biometria;
	}
	
	public boolean estaEmBase64() { return Base64.isBase64(biometria.getBytes()); } 
	
	public String getText() { return biometria; }
}
