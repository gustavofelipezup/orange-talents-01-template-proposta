package com.zup.br.proposta.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
public class Endereco {

	@NotBlank
	private String logradouro;
	@NotBlank
	private String bairro;
	@NotBlank
	private String municipio;
	@NotBlank
	private String estado;
	@NotBlank
	private String pais;
	@NotBlank
	private String cep;
	
	public Endereco(@NotBlank String logradouro, @NotBlank String bairro, @NotBlank String municipio,
			@NotBlank String estado, @NotBlank String pais, @NotBlank String cep) {
		super();
		this.logradouro = logradouro;
		this.bairro = bairro;
		this.municipio = municipio;
		this.estado = estado;
		this.pais = pais;
		this.cep = cep;
	}

	public Endereco() {
		super();
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
	
	
}
